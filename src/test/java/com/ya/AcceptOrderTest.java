package com.ya;

import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptOrderTest {
    private static OrderClient orderClient;
    private static int trackId ;

    private static CourierClient courierClient;
    private static CourierCreateData courierData;

    private static int courierId;
    int orderID;
// создать курьера
    //сздать заказ выцепить его айди
    //совместить

    @Before
    public  void setUp() {
        orderClient = new OrderClient();
        OrderData orderOne = OrderData.getRandom();
        trackId = orderClient.createOrder(orderOne).assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        System.out.println(trackId);

         orderID = orderClient.orderId(trackId);
        System.out.println(orderID);


        courierClient = new CourierClient();
        courierData = CourierCreateData.getRandom();
        courierClient.create(courierData);
        courierId = courierClient.login(CourierLoginData.from(courierData)).assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        System.out.println(courierId);
    }
//a как отменять хз, не рабоат заказы тоде не удаляет.....
    @Test
    public void acceptOrder() {
        boolean isAccept = orderClient.acceptOrder(orderID,courierId);

        assertTrue("Order is not accept", isAccept);
    }

}
