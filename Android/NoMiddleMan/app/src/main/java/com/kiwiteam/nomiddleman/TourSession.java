package com.kiwiteam.nomiddleman;

import java.util.Date;

/**
 * Created by Luis on 4/1/2015.
 */
public class TourSession {
    private String day;
    private String time;
    private int id;
    private int availability;

    public TourSession() {
        day = "";
        time = "";
        id = -1;
        availability = 0;
    }

    public TourSession(String day, String time, int id, int availability) {
        this.day = day;
        this.time = time;
        this.id = id;
        this.availability = availability;
    }

    public String getSessionDay() {
        return day;
    }

    public String getSessionTime() {
        return time;
    }

    public int getSessionID() {
        return id;
    }

    public int getAvailability() {
        return availability;
    }

    public void updateAvailability(int qty) {
        availability = availability - qty;
    }


}
