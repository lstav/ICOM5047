package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

/**
 * Created by luis.tavarez on 3/25/2015.
 */
public class TourClass {

    private String tourName = new String();
    private ArrayList<String> tourCategories = new ArrayList<>();
    private String[] tourLocation = new String[3];
    private double tourPrices;
    private ArrayList<String> tourPictures = new ArrayList<>();
    private String tourGuide = new String();
    private String tourDescription = new String();
    private int tourRating = 0;
    private ArrayList<String> tourReview = new ArrayList<>();
    private ArrayList<String> tourSessionsDate = new ArrayList<>();
    private ArrayList<String> tourSessionsTime = new ArrayList<>();
    private String tourVideos = new String();

    public TourClass(String tourName, ArrayList<String> tourCategories, String[] tourLocation, double tourPrices, ArrayList<String> tourPictures, String tourGuide, String tourDescription, int tourRating, ArrayList<String> tourReview, ArrayList<String> tourSessionsDate, ArrayList<String> tourSessionsTime, String tourVideos) {
        this.tourName = tourName;
        this.tourCategories = tourCategories;
        this.tourLocation = tourLocation;
        this.tourPrices = tourPrices;
        this.tourPictures = tourPictures;
        this.tourGuide = tourGuide;
        this.tourDescription = tourDescription;
        this.tourRating = tourRating;
        this.tourReview = tourReview;
        this.tourSessionsDate = tourSessionsDate;
        this.tourSessionsTime = tourSessionsTime;
        this.tourVideos = tourVideos;
    }

    public String getTourName() {
        return tourName;
    }

    public ArrayList<String> getTourCategories() {
        return tourCategories;
    }

    public String[] getTourLocation() {
        return tourLocation;
    }

    public double getTourPrice() {
        return tourPrices;
    }

    public ArrayList<String> getTourPictures() {
        return tourPictures;
    }

    public String getTourGuide() {
        return tourGuide;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public int getTourRating() {
        return tourRating;
    }

    public ArrayList<String> getTourReview() {
        return tourReview;
    }

    public ArrayList<String> getTourSessionsDate() {
        return tourSessionsDate;
    }

    public ArrayList<String> getTourSessionsTime() {
        return tourSessionsTime;
    }

    public String getTourVideos() {
        return tourVideos;
    }
}
