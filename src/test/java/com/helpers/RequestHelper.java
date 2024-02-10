package com.helpers;

import com.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public final class RequestHelper {

    /**
     * Plus
     */

    /**
     * Sends a GET request to the specified endpoint with optional query and path parameters.
     * The parameters should be passed in as key-value pairs.
     *
     * @param endpoint The endpoint URL with placeholders for path parameters.
     * @param params   The parameters as varargs (alternating keys and values).
     * @return The response from the request.
     */
    public static Response getRequest(String endpoint, Object... params) {
        Map<String, Object> pathParams = new HashMap<>();
        Map<String, Object> queryParams = new HashMap<>();

        // Process parameters
        for (int i = 0; i < params.length; i += 2) {
            if (i + 1 >= params.length || !(params[i] instanceof String)) {
                throw new IllegalArgumentException("Invalid or missing key-value pair for parameters");
            }
            String key = (String) params[i];
            Object value = params[i + 1];

            // Determine if key is a path or query parameter
            if (endpoint.contains("{" + key + "}")) {
                pathParams.put(key, value);
            } else {
                queryParams.put(key, value);
            }
        }

        // Create and configure the request specification
        RequestSpecification requestSpecification = given()
                .accept(ContentType.JSON)
                .header("api_key", ConfigurationReader.getProperty("api_key"))
                .queryParams(queryParams)
                .pathParams(pathParams);

        // Send the GET request
        return requestSpecification
                .when().get(endpoint)
                .then()
                .extract().response();
    }

    /**
     * Sends a DELETE request to the specified endpoint with optional path parameters.
     * The parameters should be passed in as key-value pairs.
     *
     * @param endpoint The endpoint URL with placeholders for path parameters.
     * @param params   The parameters as varargs (alternating keys and values).
     * @return The response from the request.
     */
    public static Response deleteRequest(String endpoint, Object... params) {
        Map<String, Object> pathParams = new HashMap<>();

        // Process parameters
        for (int i = 0; i < params.length; i += 2) {
            if (i + 1 >= params.length || !(params[i] instanceof String)) {
                throw new IllegalArgumentException("Invalid or missing key-value pair for parameters");
            }
            String key = (String) params[i];
            Object value = params[i + 1];

            // Add path parameters
            pathParams.put(key, value);
        }

        // Create and configure the request specification
        RequestSpecification requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("api_key", ConfigurationReader.getProperty("api_key"))
                .pathParams(pathParams);

        // Send the DELETE request
        return requestSpecification
                .when().delete(endpoint)
                .then()
                .log().body()
                .extract().response();
    }

    /**
     * Posts a request with support for a path parameter, form parameters, an optional file, and an optional JSON body.
     * This method assumes the first parameter pair is the path parameter.
     *
     * @param endpoint The endpoint URL, including placeholders for path parameters.
     * @param file     An optional file for multipart/form-data requests. Can be null.
     * @param body     An optional JSON body for the request. If provided, form parameters are ignored.
     * @param params   Parameters for the request, starting with path parameter key-value, followed by form parameters.
     * @return The response from the API call.
     */
    public static Response postRequest(String endpoint, File file, Object body, Object... params) {
        RequestSpecification requestSpecification = given()
                .accept(ContentType.JSON)
                .header("api_key", ConfigurationReader.getProperty("api_key"));

        // Process the path parameter
        if (params.length >= 2 && params[0] instanceof String) {
            String pathParamKey = (String) params[0];
            Object pathParamValue = params[1];
            endpoint = endpoint.replace("{" + pathParamKey + "}", pathParamValue.toString());
        }

        if (body != null) {
            // If a JSON body is provided, set the appropriate content type and add the body to the request.
            requestSpecification.contentType(ContentType.JSON).body(body);
        } else {
            // Handle form parameters and file for multipart/form-data
            if (file != null) {
                requestSpecification.contentType("multipart/form-data");
                requestSpecification.multiPart(file);
                // Process form parameters as part of multipart data
                for (int i = 2; i < params.length; i += 2) {
                    if (i + 1 < params.length && params[i] instanceof String) {
                        requestSpecification.multiPart((String) params[i], params[i + 1]);
                    }
                }
            } else {
                // If there's no file and no JSON body, assume form parameters with urlencoded format
                requestSpecification.contentType(ContentType.URLENC);
                for (int i = 2; i < params.length; i += 2) {
                    if (i + 1 < params.length && params[i] instanceof String) {
                        requestSpecification.formParam((String) params[i], params[i + 1]);
                    }
                }
            }
        }

        // Send the POST request
        return requestSpecification.when().post(endpoint).then().extract().response();
    }

    // Basic method with only endpoint
    public static Response postRequest(String endpoint) {
        return postRequest(endpoint, null, null, new Object[0]);
    }

    // Overload with endpoint and file
    public static Response postRequest(String endpoint, File file) {
        return postRequest(endpoint, file, null, new Object[0]);
    }

    // Overload with endpoint and body
    public static Response postRequest(String endpoint, Object body) {
        return postRequest(endpoint, null, body, new Object[0]);
    }

    // Overload with endpoint, file, and body
    public static Response postRequest(String endpoint, File file, Object body) {
        return postRequest(endpoint, file, body, new Object[0]);
    }

    /**
     * Sends a PUT request with support for a path parameter, form parameters, an optional file, and an optional JSON body.
     * This method assumes the first parameter pair is the path parameter.
     *
     * @param endpoint The endpoint URL, including placeholders for path parameters.
     * @param file     An optional file for multipart/form-data requests. Can be null.
     * @param body     An optional JSON body for the request. If provided, form parameters are ignored.
     * @param params   Parameters for the request, starting with path parameter key-value, followed by form parameters.
     * @return The response from the API call.
     */
    public static Response putRequest(String endpoint, File file, Object body, Object... params) {
        RequestSpecification requestSpecification = given()
                .accept(ContentType.JSON)
                .header("api_key", ConfigurationReader.getProperty("api_key"));

        // Process the path parameter
        if (params.length >= 2 && params[0] instanceof String) {
            String pathParamKey = (String) params[0];
            Object pathParamValue = params[1];
            endpoint = endpoint.replace("{" + pathParamKey + "}", pathParamValue.toString());
        }

        if (body != null && file == null) {
            // If a JSON body is provided and there's no file, set contentType to JSON and add the body.
            requestSpecification.contentType(ContentType.JSON).body(body);
        } else if (file != null) {
            // If a file is included, use multipart/form-data and add the file and any form parameters.
            requestSpecification.contentType("multipart/form-data").multiPart(file);
            for (int i = 2; i < params.length; i += 2) {
                if (i + 1 < params.length && params[i] instanceof String) {
                    requestSpecification.multiPart((String) params[i], params[i + 1]);
                }
            }
        } else {
            // If there's no file and body, assume form parameters with urlencoded format for compatibility.
            requestSpecification.contentType(ContentType.URLENC);
            for (int i = 2; i < params.length; i += 2) {
                if (i + 1 < params.length && params[i] instanceof String) {
                    requestSpecification.formParam((String) params[i], params[i + 1]);
                }
            }
        }

        // Execute the PUT request
        return requestSpecification.when().put(endpoint).then().extract().response();
    }


    // Basic method with only endpoint
    public static Response putRequest(String endpoint) {
        return putRequest(endpoint, null, null, new Object[0]);
    }

    // Overload with endpoint and file
    public static Response putRequest(String endpoint, File file) {
        return putRequest(endpoint, file, null, new Object[0]);
    }

    // Overload with endpoint and body
    public static Response putRequest(String endpoint, Object body) {
        return putRequest(endpoint, null, body, new Object[0]);
    }

    // Overload with endpoint, file, and body
    public static Response putRequest(String endpoint, File file, Object body) {
        return putRequest(endpoint, file, body, new Object[0]);
    }
}
