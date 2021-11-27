package com.ya;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteCourierTest {
    private  CourierClient courierClient;
    private  int courierId;

    @Before
    public  void setUp() {
        courierClient = new CourierClient();
        CourierCreateData courierData = CourierCreateData.getRandom();
        courierClient.create(courierData);
        courierId = courierClient.login(CourierLoginData.from(courierData)).assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    //проверяет, что можно удалить курьера
    @Test
    public void deleteCourierWithValidData() {
        boolean deleteCourier = courierClient.delete(courierId).assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
        assertTrue("Courier is not delete",deleteCourier);
    }
    //баг 404 ошибка вместо 400 "message": "Not Found."
    //проверяет, что нельзя удалить курьера без Id
    @Test
    public void deleteCourierWithoutId() {
        String deleteCourier = courierClient.deleteWithoutId().assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertEquals("Недостаточно данных для удаления курьера",deleteCourier);
    }
    // проверяет, что нельзя удалить курьера с некорректным ID
    @Test
    public void deleteCourierWithIncorrectId() {
        String deleteCourier = courierClient.delete(courierId + 5555 ).assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertEquals("Курьера с таким id нет.",deleteCourier);
    }
}
