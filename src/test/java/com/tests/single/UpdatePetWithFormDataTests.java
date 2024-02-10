package com.tests.single;

import com.helpers.Endpoints;
import com.helpers.RequestHelper;
import com.models.Pet;
import com.utilities.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create one pet, update and delete")
public class UpdatePetWithFormDataTests extends BaseTest {

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

    @DisplayName("Update the pet with formData")
    @Test()
    public void updatePetWithFormData() {

        Response response = RequestHelper.postRequest(Endpoints.UPDATE_PET_WITH_FORMDATA.url(), null, null,
                "petId", petId,
                "name", "testname",
                "status", "sold");

        Assertions.assertEquals((Integer) 200, response.path("code"));
        Assertions.assertEquals("unknown", response.path("type"));
        Assertions.assertEquals(petId.toString(), response.path("message"));
    }
}
