package com.ya;

public class CourierLoginData implements CourierData {
    public final String login;
    public final String password;

    public CourierLoginData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierLoginData from(CourierCreateData courierData) {
        return new CourierLoginData(courierData.login, courierData.password);
    }

    public static CourierLoginData fromInvalidDataLogin(CourierCreateData courierData) {
        return new CourierLoginData(courierData.login + "5555", courierData.password);
    }

    public static CourierLoginData fromInvalidDataPassword(CourierCreateData courierData) {
        return new CourierLoginData(courierData.login, courierData.password + "5555");
    }
}
