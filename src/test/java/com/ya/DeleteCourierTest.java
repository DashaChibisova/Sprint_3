package com.ya;

import groovy.json.JsonOutput;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteCourierTest {
    private static CourierClient courierClient;
    private static int courierId;

    @BeforeClass
    public static void setUp() {
        courierClient = new CourierClient();
        CourierCreateData courierData = CourierCreateData.getRandom();
        courierClient.create(courierData);
        courierId = courierClient.login(CourierLoginData.from(courierData)).assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }


    @Test
    public void deleteCourierWithValidData() {
        boolean deleteCourier = courierClient.delete(courierId).assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
        assertTrue("Courier is not delete",deleteCourier);
    }
//баг 404 ошибка вместо 400 "message": "Not Found."
    @Test
    @Description("Bag: Error status code 400, massage : \"Not Found.\"")
    public void deleteCourierWithoutId() {
        String deleteCourier = courierClient.deleteWithoutId().assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertEquals("Недостаточно данных для удаления курьера",deleteCourier);
    }

    @Test
    public void deleteCourierWithIncorrectId() {
        String deleteCourier = courierClient.delete(courierId + 5555 ).assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertEquals("Курьера с таким id нет.",deleteCourier);
    }
}
