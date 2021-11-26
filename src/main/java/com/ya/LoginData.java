package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class LoginData implements CourierData {
    public final String login;

    public LoginData(String login) {
        this.login = login;
    }

    public static LoginData getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        return new LoginData(login);
    }

    public static LoginData from(CourierCreateData courierData) {
        return new LoginData(courierData.login);
    }
}
