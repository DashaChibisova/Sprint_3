package com.ya;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderClient orderClient;
    private int trackId;
    private OrderTrack orderTrack;
    private String[] color;

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


    public CreateOrderTest(String color) {
        this.color = new String[]{color};
    }

    @Parameterized.Parameters
    public static Object[] getColorData() {
        return new Object[][]{
                {"GREY\" , \"BLACK"},
                {"GREY"},
                {"BLACK"},
                {""}
        };
    }

    @Test
    public void orderCanBeCreatedWithValidData() {
        OrderData orderData = OrderData.getRandom();
        orderData.setColor(color);
        trackId = orderClient.createOrder(orderData).assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        System.out.println(trackId);

        assertThat("Courier track is incorrect", trackId, is(not(0)));
    }
}
