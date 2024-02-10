package com.tests.single;

import com.helpers.Endpoints;
import com.helpers.RequestHelper;
import com.models.Pet;
import com.utilities.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Create one pet, update and delete")
public class FindPetByIdTests extends BaseTest {

    public static Pet pet = Pet.createPetReq();
    private static Long petId;

    @DisplayName("Add a new pet to the store")
    @BeforeAll
    public static void createPet() {

        Response response = RequestHelper.postRequest(Endpoints.CREATE_PET.url(), pet);
        petId = response.path("id");

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(pet.getCategory().getId(), response.path("category.id"));
        Assertions.assertEquals(pet.getCategory().getName(), response.path("category.name"));
        Assertions.assertEquals(pet.getName(), response.path("name"));
        Assertions.assertEquals(pet.getPhotoUrls().get(0), response.path("photoUrls[0]"));
        Assertions.assertEquals(pet.getTags().get(0).getId(), response.path("tags[0].id"));
        Assertions.assertEquals(pet.getTags().get(0).getName(), response.path("tags[0].name"));
        Assertions.assertEquals(pet.getStatus(), response.path("status"));
    }

    @DisplayName("Find pet by ID")
    @Test
    @Order(1)
    public void getPetById() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_ID.url(),
                "petId", petId);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(pet.getCategory().getId(), response.path("category.id"));
        Assertions.assertEquals(pet.getCategory().getName(), response.path("category.name"));
        Assertions.assertEquals(pet.getName(), response.path("name"));
        Assertions.assertEquals(pet.getPhotoUrls().get(0), response.path("photoUrls[0]"));
        Assertions.assertEquals(pet.getTags().get(0).getId(), response.path("tags[0].id"));
        Assertions.assertEquals(pet.getTags().get(0).getName(), response.path("tags[0].name"));
        Assertions.assertEquals(pet.getStatus(), response.path("status"));
    }

    @DisplayName("Find pet by ID -invalid id")
    @Test
    @Order(2)
    public void getPetById2() {

        Response response = newGetRequest(Endpoints.PET_FIND_BY_ID.url(),
                "petId", "qwe!'^^+");

        Assertions.assertEquals(404, response.statusCode());
    }
}
