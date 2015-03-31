package com.kiwiteam.nomiddleman;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Luis on 3/19/2015.
 */
public class Tour {
    private String name;
    private double price;
    private ArrayList<String> picture;
    private int id;

    public Tour(String name, double price, ArrayList<String> picture, int id) {
        super();

        this.name = name;
        this.price = price;
        this.picture = picture;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getPictures() {
        return picture;
    }

    public int getId() {
        return id;
    }

}
