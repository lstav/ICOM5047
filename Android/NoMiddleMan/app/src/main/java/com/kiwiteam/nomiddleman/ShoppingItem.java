package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

public class ShoppingItem {
    private int tourID;
    private TourClass tour;
    private int quantity;
    private String date;
    private String time;

    public ShoppingItem(TourClass tour, int quantity, String date, String time) {
        this.tourID = tourID;
        this.tour = tour;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
    }

    public int getTourID() {
        return tourID;
    }

    public String getTourName() {
        return tour.getTourName();
    }

    public double getTourPrice() {
        return tour.getTourPrice()*quantity;
    }

    public ArrayList<String> getTourPicture() {
        return tour.getTourPictures();
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
}