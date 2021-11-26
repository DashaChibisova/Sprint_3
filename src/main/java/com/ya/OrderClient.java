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
    public int orderId(int track) {
        return given()
                .spec(getBaseSpec())
                .get(COURIER_PATH+"track?t=" + track)
                .then()
                .statusCode(200)
                .extract()
                .path("order.id");
    }


    @Step
    public Response receiveOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .get(COURIER_PATH + track);
    }

    @Step
    public String receiveOrderWithoutTrack() {
        return given()
                .spec(getBaseSpec())
                .get(COURIER_PATH + "track?t=")
                .then()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step
    public String incorrectTrack(int track) {
        return given()
                .spec(getBaseSpec())
                .get(COURIER_PATH + "track?t=" + track)
                .then()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step
    public boolean acceptOrder(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .put(COURIER_PATH + "accept/" + orderId + "?courierId=" + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

    }

}

