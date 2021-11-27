package com.ya;

import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import org.junit.After;
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
    static int orderID;
    private static int invalidData = 5555;

    @BeforeClass
    public  static void setUp() {
        orderClient = new OrderClient();
        OrderData orderOne = OrderData.getRandom();
        trackId = orderClient.createOrder(orderOne).assertThat()
                .statusCode(201)
                .extract()
                .path("track");

         orderID = orderClient.orderId(trackId).assertThat().statusCode(200)
                 .extract()
                 .path("order.id");

        courierClient = new CourierClient();
        courierData = CourierCreateData.getRandom();
        courierClient.create(courierData);
        courierId = courierClient.login(CourierLoginData.from(courierData)).assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        System.out.println(courierId);
    }

    //баг не отменяет заказы!!! выдает 400 ошибку,"message" "Недостаточно данных для поиска"
    @After
    public void tearDown() {
//        orderTrack = new OrderTrack(trackId);
//        boolean cancelOrder = orderClient.cancel(orderTrack);
        // assertTrue("Order is not cancel", cancelOrder); но это уже на проверку
    }
//a как отменять хз, не рабоат заказы тоде не удаляет.....
    @Test
    public void acceptOrderWithValidData() {
        boolean isAccept = orderClient.acceptOrder(orderID,courierId).assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

        assertTrue("Order is not accept", isAccept);
    }
    @Test
    public void acceptOrderWithoutIncorrectOrderIdReturnError() {
        String accept = orderClient.acceptOrder(invalidData,courierId).assertThat()
                .statusCode(404)
                .extract()
                .path("message");

        assertEquals("Заказа с таким id не существует", accept);
    }

    @Test
    public void acceptOrderWithoutIncorrectCourierIdReturnError() {
        String accept = orderClient.acceptOrder(orderID,invalidData).assertThat()
                .statusCode(404)
                .extract()
                .path("message");

        assertEquals("Курьера с таким id не существует", accept);
    }

    @Test
    public void acceptOrderWhichInWorkReturnError() {
        orderClient.acceptOrder(orderID,courierId);
        String accept = orderClient.acceptOrder(orderID,courierId).assertThat()
                .statusCode(409)
                .extract()
                .path("message");

        assertEquals("Этот заказ уже в работе", accept);
    }
    //баг выходит 404 ошибка
    @Test
    @Description("Bag: Error status code 404")
    public void acceptOrderWithoutOrderIdReturnError() {
        String accept = orderClient.acceptOrderWithoutOrderId(courierId).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для поиска", accept);
    }

    @Test
    public void acceptOrderWithoutCourierIdReturnError() {
        String accept = orderClient.acceptOrderWithoutCourierId(orderID).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для поиска", accept);
    }
}
