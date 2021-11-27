package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderData {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final int metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public String[] color;

    public OrderData(String firstName, String lastName, String address,
                     int metroStation, String phone, int rentTime,
                     String deliveryDate, String comment, String color[]) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static OrderData getRandom() {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final int metroStation = (int) (Math.random());
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final int rentTime = (int) (Math.random());
        final String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final String[] color = {RandomStringUtils.randomAlphabetic(10)};
        return new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate,
                comment, color);
    }

    public void setColor(String[] color) {
        this.color = color;
    }
}
