package com.ya;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class CourierCreateTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId).assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Test
    public void courierCanBeCreatedWithValidDataAndLogin() {
        CourierCreateData courierData = CourierCreateData.getRandom();
        boolean isCourierCreated = courierClient.create(courierData)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
        courierId = courierClient.login(CourierLoginData.from(courierData))
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        assertTrue("Courier is not created", isCourierCreated);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    //"message""Этот логин уже используется." должно быть это баг
    @Test
    public void courierShouldNotBeReCreated() {
        CourierCreateData courierData = CourierCreateData.getRandom();
        boolean isCourierCreated = courierClient.create(courierData)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        courierId = courierClient.login(CourierLoginData.from(courierData)) .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        String isNotCourierCreated = courierClient.create(courierData).assertThat()
                .statusCode(409)
                .extract()
                .path("message");

        assertTrue("Courier is not created", isCourierCreated);
        assertEquals("Courier is  recreated", "Этот логин уже используется. Попробуйте другой.", isNotCourierCreated);
    }
}
