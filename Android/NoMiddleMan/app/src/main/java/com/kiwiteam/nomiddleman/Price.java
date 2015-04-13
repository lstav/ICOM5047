package com.kiwiteam.nomiddleman;

/**
 * Created by Luis on 4/12/2015.
 */
public class Price {
    public static double getDouble(String price) {
        String substring = price.substring(1);

        double Price = Double.parseDouble(substring);
        return Price;
    }
}
