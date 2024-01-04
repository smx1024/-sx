package com.sx.orderservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtils {
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        String str = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            str += random.nextInt(10);
        }
        return date + str;
    }
}
