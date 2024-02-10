package com.utilities;

import com.helpers.RequestHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class BaseTest {
    @BeforeAll
    public static void init() {
        baseURI = ConfigurationReader.getProperty("base_url");
    }

    public Response newGetRequest(String endpoint, Object... params){
        return RequestHelper.getRequest(endpoint, params);
    }
    public Response newDeleteReques(String endpoint, Object... params){
        return RequestHelper.deleteRequest(endpoint, params);
    }
}
