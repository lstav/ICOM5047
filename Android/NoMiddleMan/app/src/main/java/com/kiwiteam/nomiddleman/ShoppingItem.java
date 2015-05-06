package com.kiwiteam.nomiddleman;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ShoppingItem {
    private int tourID;
    private Tour tour;
    private int sessionID;
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

    /**
     * Constructor for shopping item
     * @param tour
     * @param sessionID
     * @param quantity
     * @param date
     * @param time
     * @param isActive
     */
    public ShoppingItem(Tour tour, int sessionID, int quantity, String date, String time, boolean isActive) {
        this.tourID = tour.getId();
        this.tour = tour;
        this.sessionID = sessionID;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.isActive = isActive;
    }

    /**
     * Gets tour ID
     * @return
     */
    public int getTourID() {
        return tourID;
    }

    /**
     * Gets tour name
     * @return
     */
    public String getTourName() {
        return tour.getName();
    }

    /**
     * Gets tour price
     * @return
     */
    public double getTourPrice() {
        return tour.getPrice();
    }

    /**
     * Gets session ID
     * @return
     */
    public int getSessionID() {
        return sessionID;
    }

    /**
     * Gets tours pictures
     * @return
     */
    public ArrayList<Bitmap> getTourPicture() {
        return tour.getPictures();
    }

    /**
     * Gets quantity
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets tour day
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets tour time
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * Adds quantity to item
     * @param i
     */
    public void addQuantity(int i) {
        quantity = quantity + i;
    }

    /**
     * Tour session is active
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Overrides equals method to check equality of two shopping cart items
     * @param sItem
     * @return
     */
    public boolean equals(ShoppingItem sItem) {
        if(this.getTime().equals(sItem.getTime()) && this.getDate().equals(sItem.getDate()) && this.getTourID() == sItem.getTourID()) {
            return true;
        }
        return false;
    }
}