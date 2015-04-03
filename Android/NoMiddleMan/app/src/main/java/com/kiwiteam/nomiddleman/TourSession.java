package com.kiwiteam.nomiddleman;

import java.util.Date;

/**
 * Created by Luis on 4/1/2015.
 */
public class TourSession {
    private String day;
    private String time;
    private int id;
    private boolean isActive;

    public TourSession() {
        day = "";
        time = "";
        id = -1;
        isActive = false;
    }

    public TourSession(String day, String time, int id, boolean isActive) {
        this.day = day;
        this.time = time;
        this.id = id;
        this.isActive = isActive;
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

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }


}
