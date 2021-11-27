package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutPasswordData implements Courier {
    public final String login;
    public final String firstName;

    public CourierWithoutPasswordData(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public static CourierWithoutPasswordData getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithoutPasswordData(login, firstName);
    }
}

