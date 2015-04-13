package com.kiwiteam.nomiddleman;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Luis on 3/19/2015.
 */
public class Tour {
    private String name;
    private double price;
    private ArrayList<Bitmap> picture;
    private int id;
    private double extremeness;

    public Tour(String name, double price, ArrayList<Bitmap> picture, int id, double extremeness) {
        super();

        this.name = name;
        this.price = price;
        this.picture = picture;
        this.id = id;
        this.extremeness = extremeness;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Bitmap> getPictures() {
        return picture;
    }

    public int getId() {
        return id;
    }

    public double getExtremeness() {
        return extremeness;
    }

}
