package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordData implements CourierData {
    public final String password;

    public PasswordData(String password) {
        this.password = password;
    }

    public static PasswordData getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new PasswordData(password);
    }

    public static PasswordData from(CourierCreateData courierData) {
        return new PasswordData(courierData.password);
    }
}

