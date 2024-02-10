package com.tests.single;

import com.helpers.Endpoints;
import com.helpers.RequestHelper;
import com.models.Pet;
import com.utilities.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create one pet, update and delete")
public class AddNewPetTests extends BaseTest {

    @DisplayName("Add a new pet to the store")
    @Test()
    public void addNewPet1() {
        Pet pet = Pet.createPetReq();

        Response response = RequestHelper.postRequest(Endpoints.CREATE_PET.url(), pet);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(pet.getCategory().getId(), response.path("category.id"));
        Assertions.assertEquals(pet.getCategory().getName(), response.path("category.name"));
        Assertions.assertEquals(pet.getName(), response.path("name"));
        Assertions.assertEquals(pet.getPhotoUrls().get(0), response.path("photoUrls[0]"));
        Assertions.assertEquals(pet.getTags().get(0).getId(), response.path("tags[0].id"));
        Assertions.assertEquals(pet.getTags().get(0).getName(), response.path("tags[0].name"));
        Assertions.assertEquals(pet.getStatus(), response.path("status"));
    }

    @DisplayName("Add a new pet to the store - with missing Category field")
    @Test
    public void addNewPet() {
        Pet pet = Pet.createPetReq();
        pet.setCategory(null);

        Response response = RequestHelper.postRequest(Endpoints.CREATE_PET.url(), pet);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(pet.getName(), response.path("name"));
        Assertions.assertEquals(pet.getPhotoUrls().get(0), response.path("photoUrls[0]"));
        Assertions.assertEquals(pet.getTags().get(0).getId(), response.path("tags[0].id"));
        Assertions.assertEquals(pet.getTags().get(0).getName(), response.path("tags[0].name"));
        Assertions.assertEquals(pet.getStatus(), response.path("status"));
    }

    //Normally we expect to get 400 here! Assertions can be adjusted as required
    @DisplayName("Add a new pet to the store - with missing fields")
    @Test
    public void addNewPet2() {
        Pet pet = Pet.createPetReq();
        pet.setCategory(null);
        pet.setName(null);
        pet.setPhotoUrls(null);
        pet.setTags(null);
        pet.setStatus(null);
        Response response = RequestHelper.postRequest(Endpoints.CREATE_PET.url(), pet);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.path("id"));
    }
}
