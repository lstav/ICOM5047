package com.kiwiteam.nomiddleman;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ShoppingItem {
    private int tourID;
    private Tour tour;
    private int quantity;
    private String date;
    private String time;
    private boolean isActive;

    public ShoppingItem() {
        //this.tour = new TourClass();
        this.quantity = 0;
        this.date = "";
        this.time = "";
    }

    public ShoppingItem(Tour tour, int quantity, String date, String time, boolean isActive) {
        this.tourID = tour.getId();
        this.tour = tour;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.isActive = isActive;
    }

    public int getTourID() {
        return tourID;
    }

    public String getTourName() {
        return tour.getName();
    }

    public double getTourPrice() {
        return tour.getPrice();
    }

    public ArrayList<Bitmap> getTourPicture() {
        return tour.getPictures();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void addQuantity(int i) {
        quantity = quantity + i;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean equals(ShoppingItem sItem) {
        if(this.getTime().equals(sItem.getTime()) && this.getDate().equals(sItem.getDate()) && this.getTourID() == sItem.getTourID()) {
            return true;
        }
        return false;
    }
}