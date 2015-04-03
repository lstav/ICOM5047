package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

/**
 * Created by luis.tavarez on 3/25/2015.
 */
public class TourClass {

    private int tourID;
    private String tourName;
    private ArrayList<String> tourCategories;
    private String[] tourLocation;
    private double tourPrices;
    private ArrayList<String> tourPictures;
    private String tourGuide;
    private String tourDescription;
    private ArrayList<Double> tourRatings;
    private ArrayList<String> tourReviews;
    private ArrayList<TourSession> tourSessions;
    private ArrayList<String> tourSessionsDate;
    private ArrayList<String> tourSessionsTime;
    private String tourVideos;

    public TourClass() {
        this.tourName = new String();
        this.tourCategories = new ArrayList<>();
        this.tourLocation = new String[3];
        this.tourPrices = 0.00;
        this.tourPictures = new ArrayList<>();
        this.tourGuide = new String();
        this.tourDescription = new String();
        this.tourRatings = new ArrayList<>();
        this.tourReviews = new ArrayList<>();
        this.tourSessions = new ArrayList<>();
        this.tourSessionsDate = new ArrayList<>();
        this.tourSessionsTime = new ArrayList<>();
        this.tourVideos = new String();
    }

    public TourClass(String tourName, ArrayList<String> tourCategories, String[] tourLocation, double tourPrices, ArrayList<String> tourPictures, String tourGuide, String tourDescription, ArrayList<Double> tourRating, ArrayList<String> tourReview, ArrayList<TourSession> tourSessions, String tourVideos) {
        this.tourName = tourName;
        this.tourCategories = tourCategories;
        this.tourLocation = tourLocation;
        this.tourPrices = tourPrices;
        this.tourPictures = tourPictures;
        this.tourGuide = tourGuide;
        this.tourDescription = tourDescription;
        this.tourRatings = tourRating;
        this.tourReviews = tourReview;
        this.tourSessions = tourSessions;
        this.tourSessionsDate = new ArrayList<>();
        this.tourSessionsTime = new ArrayList<>();
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

    public double getTourRating() {
        double tourRating = 0.00;
        for(int i = 0; i < tourRatings.size(); i++) {
            tourRating = tourRating + tourRatings.get(i);
        }

        return tourRating;
    }

    public ArrayList<Double> getTourRatings() {
        return tourRatings;
    }

    public ArrayList<String> getTourReview() {
        return tourReviews;
    }

    public ArrayList<String> getTourSessionsDate() {
        for (int i=0; i<tourSessions.size(); i++) {
            if (tourSessions.get(i).isActive() && !tourSessionsDate.contains(tourSessions.get(i).getSessionDay())) {
                tourSessionsDate.add(tourSessions.get(i).getSessionDay());
            }
        }
        return tourSessionsDate;
    }

    public ArrayList<String> getTourSessionsTime(String date) {
        tourSessionsTime.clear();
        for (int i=0; i<tourSessions.size(); i++) {
            if (tourSessions.get(i).isActive()) {
                if(tourSessions.get(i).getSessionDay().equals(date) && !tourSessionsTime.contains(tourSessions.get(i).getSessionTime())) {
                    tourSessionsTime.add(tourSessions.get(i).getSessionTime());
                }
            }
        }
        return tourSessionsTime;
    }

    public ArrayList<Integer> getTourSessionsID() {
        ArrayList<Integer> IDs = new ArrayList<>();
        for (int i=0; i<tourSessions.size(); i++) {
            IDs.add(tourSessions.get(i).getSessionID());
        }
        return IDs;
    }

    public ArrayList<TourSession> getTourSessions() {
        return tourSessions;
    }

    public String getTourVideos() {
        return tourVideos;
    }

    public boolean sessionIsActive(int id) {
        for(int i=0; i<tourSessions.size(); i++) {
            if (tourSessions.get(i).getSessionID() == id) {
                return tourSessions.get(i).isActive();
            }
        }
        return false;
    }

    public boolean isActive() {
        for (int i=0; i<tourSessions.size(); i++) {
            if (tourSessions.get(i).isActive()) {
                return true;
            }
        }
        return false;
    }
}
