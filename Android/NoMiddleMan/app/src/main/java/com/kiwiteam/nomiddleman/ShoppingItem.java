package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

public class ShoppingItem {
    private int tourID;
    private TourClass tour;
    private int quantity;
    private String date;
    private String time;

    public ShoppingItem() {
        //this.tour = new TourClass();
        this.quantity = 0;
        this.date = "";
        this.time = "";
    }

    public ShoppingItem(TourClass tour, int quantity, String date, String time) {
        this.tourID = tour.getTourID();
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

    /*public ArrayList<String> getTourPicture() {
        return tour.getTourPictures();
    }*/

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
        for (int i=0; i<tour.getTourSessions().size(); i++) {
            if (tour.getTourSessions().get(i).getSessionDay().matches(date) &&
                    tour.getTourSessions().get(i).getSessionTime().matches(time)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(ShoppingItem sItem) {
        if(this.getTime().equals(sItem.getTime()) && this.getDate().equals(sItem.getDate()) && this.getTourID() == sItem.getTourID()) {
            return true;
        }
        return false;
    }
}