package com.ya;

import org.junit.*;

import static org.junit.Assert.*;

public class CourierLoginTest {

    private static CourierClient courierClient;
    private static CourierCreateData courierData;

    private static int courierId;

    @BeforeClass
    public static void setUp() {
        courierClient = new CourierClient();
        courierData = CourierCreateData.getRandom();
        courierClient.create(courierData);
        courierId = courierClient.login(CourierLoginData.from(courierData))
                .extract()
                .path("id");
    }

    @AfterClass
    public static void tearDown() {
        courierClient.delete(courierId);
    }
   //проверяет, что нельзя залогиниться без логина и ответ
    @Test
    public void courierNotCanLoginWithoutLogin() {
        PasswordData courierLogin = PasswordData.from(courierData);
        String isNotLoginCourier = courierClient.login(courierLogin).assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Courier is login", "Недостаточно данных для входа", isNotLoginCourier);
    }


    //баг висит и выдает 504 ошибку
    //проверяет, что нельзя залогиниться без пароля и ответ
    @Test
    public void courierNotCanLoginWithoutPassword() {
        LoginData courierLogin = LoginData.from(courierData);
        String isNotCourierPassword = courierClient.login(courierLogin)
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Courier is  login", "Недостаточно данных для входа", isNotCourierPassword);
    }
    //проверяет, что нельзя залогиниться с неправильным паролем или логином и ответ
    @Test
    public void courierLoginWithInvalidData() {
        String isInvalidLogin = courierClient.login(CourierLoginData.fromInvalidDataLogin(courierData))
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        String isInvalidPassword = courierClient.login(CourierLoginData.fromInvalidDataPassword(courierData))
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");

        assertEquals("Courier is login", "Учетная запись не найдена", isInvalidLogin);
        assertEquals("Courier is login", "Учетная запись не найдена", isInvalidPassword);
    }
}
