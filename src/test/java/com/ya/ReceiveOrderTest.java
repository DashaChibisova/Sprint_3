package com.ya;

import io.restassured.response.Response;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class ReceiveOrderTest {
    private static OrderClient orderClient;
    private static int trackId ;


    @BeforeClass
    public static void setUp() {
        orderClient = new OrderClient();
        OrderData orderOne = OrderData.getRandom();
        trackId = orderClient.createOrder(orderOne).assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        System.out.println(trackId);
    }
    //баг не отменяет заказы!!! выдает 400 ошибку,"message" "Недостаточно данных для поиска"
    @After
    public void tearDown() {
//        orderTrack = new OrderTrack(trackId);
//        boolean cancelOrder = orderClient.cancel(orderTrack);
        // assertTrue("Order is not cancel", cancelOrder); но это уже на проверку
    }
    @Test
    public void receiveOrderSuccessful() {
        ValidatableResponse order = orderClient.receiveOrder(trackId);
        order.assertThat().body("data.order", not(emptyArray())).log().all();
        order.assertThat().statusCode(200);
    }
    @Test
    public void receiveErrorWithoutTrack() {
        String order = orderClient.receiveOrderWithoutTrack().assertThat().statusCode(400)
                .extract()
                .path("message");
        assertEquals("Недостаточно данных для поиска",order);
    }
    @Test
    public void receiveErrorWithIncorrectTrack() {
        String order = orderClient.receiveOrder(trackId + 5555).statusCode(404)
                .extract()
                .path("message");
        assertEquals("Заказ не найден",order);
    }
}
