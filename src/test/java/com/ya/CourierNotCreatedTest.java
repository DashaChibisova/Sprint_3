package com.ya;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierNotCreatedTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void courierShouldNotBeCreatedWithoutPassword() {
        CourierWithoutPasswordData courier = CourierWithoutPasswordData.getRandom();
        String notCreate = courierClient.create(courier).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Courier should not be created", "Недостаточно данных для создания учетной записи", notCreate);
    }

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
