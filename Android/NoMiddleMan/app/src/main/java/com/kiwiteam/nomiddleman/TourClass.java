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
    private ArrayList<RatingClass> tourRatings;
    private ArrayList<Double> ratings;
    private ArrayList<String> reviews;
    private ArrayList<TourSession> tourSessions;
    private ArrayList<String> tourSessionsDate;
    private ArrayList<String> tourSessionsTime;
    private String tourVideos;
    private double extremeness;

    public TourClass() {
        this.tourID = -1;
        this.tourName = new String();
        this.tourCategories = new ArrayList<>();
        this.tourLocation = new String[3];
        this.tourPrices = 0.00;
        this.tourPictures = new ArrayList<>();
        this.tourGuide = new String();
        this.tourDescription = new String();
        this.tourRatings = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.tourSessions = new ArrayList<>();
        this.tourSessionsDate = new ArrayList<>();
        this.tourSessionsTime = new ArrayList<>();
        this.tourVideos = new String();
        this.extremeness = 0.00;
    }

    public TourClass(int tourID, String tourName, String[] tourLocation, double tourPrices, ArrayList<String> tourPictures, String tourGuide, String tourDescription, ArrayList<RatingClass> tourRatings, ArrayList<TourSession> tourSessions, String tourVideos, double extremeness) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.tourLocation = tourLocation;
        this.tourPrices = tourPrices;
        this.tourPictures = tourPictures;
        this.tourGuide = tourGuide;
        this.tourDescription = tourDescription;
        this.tourRatings = tourRatings;
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.tourSessions = tourSessions;
        this.tourSessionsDate = new ArrayList<>();
        this.tourSessionsTime = new ArrayList<>();
        this.tourVideos = tourVideos;
        this.extremeness = extremeness;
    }

    public int getTourID() {
        return tourID;
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
            tourRating = tourRating + tourRatings.get(i).getRating();
        }

        return tourRating/tourRatings.size();
    }

    public void rate(double rating, String review) {
        tourRatings.add(new RatingClass(rating, review));
    }

    public ArrayList<RatingClass> getAllRatings() {
        return tourRatings;
    }

    public ArrayList<Double> getTourRatings() {
        ratings.clear();
        for(int i = 0; i<tourRatings.size(); i++) {
            ratings.add(tourRatings.get(i).getRating());
        }
        return ratings;
    }

    public ArrayList<String> getTourReview() {
        reviews.clear();
        for(int i = 0; i<tourRatings.size(); i++) {
            reviews.add(tourRatings.get(i).getReview());
        }
        return reviews;
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

    public double getExtremeness() {
        return extremeness;
    }

    public void setExtremeness(double extremeness) {
        this.extremeness = extremeness;
    }
}
