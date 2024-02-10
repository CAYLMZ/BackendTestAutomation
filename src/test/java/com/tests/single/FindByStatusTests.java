package com.tests.single;

import com.helpers.Endpoints;
import com.utilities.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Find pets by status")
public class FindByStatusTests extends BaseTest {

    @DisplayName("Find pets by status -available")
    @Test
    public void findPetByStatus1() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_STATUS.url(),
                "status", "available");

        List<String> statusList = response.path("status");
        boolean allAvailable = statusList.stream()
                .allMatch(status -> status.equals("available"));

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(allAvailable, "Not all items in the list are 'available'");
    }

    @DisplayName("Find pets by status -pending")
    @Test
    public void findPetByStatus2() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_STATUS.url(),
                "status", "pending");

        List<String> statusList = response.path("status");
        boolean allPending = statusList.stream()
                .allMatch(status -> status.equals("pending"));

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(allPending, "Not all items in the list are 'pending'");
    }

    @DisplayName("Find pets by status -sold")
    @Test
    public void findPetByStatus3() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_STATUS.url(),
                "status", "sold");

        List<String> statusList = response.path("status");
        boolean allSold = statusList.stream()
                .allMatch(status -> status.equals("sold"));

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(allSold, "Not all items in the list are 'sold'");
    }

    @DisplayName("Find pets by status -invalid data")
    @Test
    public void findPetByStatus4() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_STATUS.url(),
                "status", "test");

        List<String> statusList = response.path("status");

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(statusList.isEmpty(), "Not empty list");
    }
}
