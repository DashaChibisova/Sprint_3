package com.ya;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CourierNotCreatedTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    //проверяет, что нельзя создать курьера без пароля и ответ
    @Test
    public void courierShouldNotBeCreatedWithoutPassword() {
        CourierWithoutPasswordData courier = CourierWithoutPasswordData.getRandom();
        String notCreate = courierClient.create(courier).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Courier should not be created", "Недостаточно данных для создания учетной записи", notCreate);
    }

    //проверяет, что нельзя создать курьера без логина и ответ
    @Test
    public void courierShouldNotBeCreatedWithoutLogin() {
        CourierWithoutLoginData courier = CourierWithoutLoginData.getRandom();
        String notCreate = courierClient.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Courier should not be created", "Недостаточно данных для создания учетной записи", notCreate);
    }
}
