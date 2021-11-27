package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private static final String COURIER_PATH = "api/v1/orders/";


    @Step
    public ValidatableResponse createOrder(OrderData order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step
    public boolean cancel(OrderTrack trackOrder) {
        return given()
                .spec(getBaseSpec())
                .body(trackOrder)
                .when()
                .put(COURIER_PATH + "cancel")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

    }

    @Step
    public Response orderResponseBody() {
        return given()
                .spec(getBaseSpec())
                .get(COURIER_PATH);
    }
    @Step
    public ValidatableResponse orderId(int track) {
        return given()
                .spec(getBaseSpec())
                .get(COURIER_PATH+"track?t=" + track)
                .then();
    }


    @Step
    public ValidatableResponse receiveOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .get(COURIER_PATH + "track")
                .then();
    }

    @Step
    public ValidatableResponse receiveOrderWithoutTrack() {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", "")
                .get(COURIER_PATH + "track")
                .then();
    }


    @Step
    public ValidatableResponse acceptOrder(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .put(COURIER_PATH + "accept/" + orderId)
                .then();

    }

    @Step
    public ValidatableResponse acceptOrderWithoutOrderId(int courierId) {
        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .put(COURIER_PATH + "accept/")
                .then();

    }

    @Step
    public ValidatableResponse acceptOrderWithoutCourierId(int orderId) {
        return given()
                .spec(getBaseSpec())
                .put(COURIER_PATH + "accept/" + orderId)
                .then();

    }

}

