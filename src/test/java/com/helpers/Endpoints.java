package com.helpers;

public enum Endpoints {
    CHECK_EMAIL("api/check-email"),
    CONFIRM_EMAIL("api/confirm-email"),
    PET_FIND_BY_STATUS("/pet/findByStatus"),
    PET_FIND_BY_ID("/pet/{petId}"),
    CREATE_PET("/pet"),
    UPDATE_PET("/pet"),
    DELETE_PET("/pet/{petId}"),
    UPDATE_PET_WITH_FORMDATA("/pet/{petId}");

    private final String url;

    Endpoints(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
