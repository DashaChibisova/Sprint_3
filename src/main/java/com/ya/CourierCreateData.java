package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCreateData implements Courier {
    public final String login;
    public final String password;
    public final String firstName;

    public CourierCreateData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierCreateData getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreateData(login, password, firstName);
    }
}
