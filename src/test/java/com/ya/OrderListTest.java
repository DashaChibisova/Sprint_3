package com.ya;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

public class OrderListTest {

    private OrderClient orderClient;
    private ArrayList<Integer> trackId = new ArrayList<>();
    private OrderTrack orderTrack;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    //баг не отменяет заказы!!! выдает 400 ошибку,"message" "Недостаточно данных для поиска"
    @After
    public void tearDown() {
//        orderTrack = new OrderTrack(trackId);
//        boolean cancelOrder = orderClient.cancel(orderTrack);
        // assertTrue("Order is not cancel", cancelOrder); но это уже на проверку
    }

    @Test
    public void checkReturnListOrderData() {
        for (int i = 0; i < 1; i++) {
            OrderData orderOne = OrderData.getRandom();
            trackId.add(orderClient.createOrder(orderOne).assertThat()
                    .statusCode(201)
                    .extract()
                    .path("track"));
        }

       JsonPath jsonPath = orderClient.orderResponseBody().getBody().jsonPath();
        List<LinkedHashMap<String, Object>> orderLevelReasons = jsonPath.getList("orders");
        String s = null;
        // берем элементы массива
        for (int i = 0; i < orderLevelReasons.size(); i++) {
            s = String.valueOf(orderLevelReasons.get(i).get("id"));
            System.out.println(s);
        }

        System.out.println(orderLevelReasons.size());
        System.out.println(s);
        System.out.println(s.matches(".(id=)\\d+"));
    }
}
