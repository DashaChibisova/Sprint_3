package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutLoginData implements CourierData {
    public final String password;
    public final String firstName;

    public CourierWithoutLoginData(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierWithoutLoginData getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithoutLoginData(password, firstName);
    }
}
