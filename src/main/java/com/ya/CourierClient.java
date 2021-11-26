package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step
    public ValidatableResponse create(CourierData courierData) {
        return given()
                .spec(getBaseSpec())
                .body(courierData)
                .when()
                .post(COURIER_PATH)
                .then();

    }

    @Step
    public ValidatableResponse login(CourierData courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }

    @Step
    public ValidatableResponse deleteWithoutId() {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH)
                .then();
    }
}
