package com.tests.scenario;

import com.helpers.Endpoints;
import com.helpers.RequestHelper;
import com.models.Pet;
import com.utilities.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Create one pet, update and delete")
public class PetCrudTests extends BaseTest {

    public static Pet pet = Pet.createPetReq();
    public static Pet updatedPet;
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

    @DisplayName("Update the pet")
    @Test
    @Order(2)
    public void updatePet() {

        updatedPet = Pet.updatedPetReq(petId);
        Response response = RequestHelper.putRequest(Endpoints.UPDATE_PET.url(), updatedPet);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(updatedPet.getCategory().getId(), response.path("category.id"));
        Assertions.assertEquals(updatedPet.getCategory().getName(), response.path("category.name"));
        Assertions.assertEquals(updatedPet.getName(), response.path("name"));
        Assertions.assertEquals(updatedPet.getPhotoUrls().get(0), response.path("photoUrls[0]"));
        Assertions.assertEquals(updatedPet.getTags().get(0).getId(), response.path("tags[0].id"));
        Assertions.assertEquals(updatedPet.getTags().get(0).getName(), response.path("tags[0].name"));
        Assertions.assertEquals(updatedPet.getStatus(), response.path("status"));
    }

    @DisplayName("Delete the pet")
    @Test
    @Order(3)
    public void deletePet() {

        Response response = newDeleteReques(Endpoints.DELETE_PET.url(),
                "petId", petId);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("unknown", response.path("type"));
        Assertions.assertEquals(petId.toString(), response.path("message"));

    }
}
