package com.models;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;


    public static Pet createPetReq() {
        Pet newPet = new Pet();
        Faker faker = new Faker();
        newPet.category = Category.createCategory();
        newPet.name = faker.animal().name();

        List<String> photoUrlsList = new ArrayList<>();
        photoUrlsList.add(PhotoUrls.createPhotoUrls());
        newPet.photoUrls = photoUrlsList;

        newPet.tags = Tags.createTags();
        newPet.status = faker.options().option("available", "sold", "pending");

        return newPet;
    }

    public static Pet updatedPetReq(Long petId) {
        Pet updatedPet = new Pet();
        Faker faker = new Faker();
        updatedPet.id = petId;
        updatedPet.category = Category.createCategory();
        updatedPet.name = faker.animal().name();

        List<String> photoUrlsList = new ArrayList<>();
        photoUrlsList.add(PhotoUrls.createPhotoUrls());
        updatedPet.photoUrls = photoUrlsList;

        updatedPet.tags = Tags.createTags();
        updatedPet.status = faker.options().option("available", "sold", "pending");

        return updatedPet;
    }

    @Getter
    @Setter
    @ToString
    public static class Category {

        private Integer id;
        private String name;

        public static Category createCategory() {
            Category newCategory = new Category();
            Faker faker = new Faker();
            newCategory.id = faker.number().randomDigitNotZero();
            newCategory.name = faker.animal().name();

            return newCategory;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class PhotoUrls {

        private String photoUrls;

        public static String createPhotoUrls() {
            PhotoUrls newPhotoUrls = new PhotoUrls();
            Faker faker = new Faker();
            newPhotoUrls.photoUrls = faker.internet().url();
            return newPhotoUrls.photoUrls;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Tags {

        private Integer id;
        private String name;

        public static List<Tags> createTags() {
            Tags newTags = new Tags();
            Faker faker = new Faker();
            newTags.id = faker.number().randomDigitNotZero();
            newTags.name = faker.animal().name();

            List<Tags> listTags = new ArrayList<>();
            listTags.add(newTags);

            return listTags;
        }
    }
}
