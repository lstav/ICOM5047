package com.kiwiteam.nomiddleman;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by luis.tavarez on 3/25/2015.
 */
public class TourClass {

    private int tourID;
    private String tourName;
    private String tourDescription;
    private String facebook;
    private String youtube;
    private String instagram;
    private String twitter;
    private double tourPrice;
    private double extremeness;
    private ArrayList<Bitmap> tourPictures;
    private String tourAddress;
    private String guideEmail;
    private String guideName;
    private String guideLicense;
    private String company;
    private String telephone;
    private double averageRating;
    private int rateCount;

    private ArrayList<RatingClass> tourRatings;
    private ArrayList<Double> ratings;
    private ArrayList<String> reviews;
    private ArrayList<TourSession> tourSessions;
    private ArrayList<String> tourSessionsDate;
    private ArrayList<String> tourSessionsTime;

    public TourClass(int tourID, String tourName, String tourDescription, String facebook, String youtube, String instagram, String twitter, double tourPrice, double extremeness, ArrayList<Bitmap> tourPictures, String tourAddress, String guideEmail, String guideName, String guideLicense, String company, String telephone, double averageRating, int rateCount, ArrayList<RatingClass> tourRatings, ArrayList<TourSession> tourSessions) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.facebook = facebook;
        this.youtube = youtube;
        this.instagram = instagram;
        this.twitter = twitter;
        this.tourPrice = tourPrice;
        this.extremeness = extremeness;
        this.tourPictures = tourPictures;
        this.tourAddress = tourAddress;
        this.guideEmail = guideEmail;
        this.guideName = guideName;
        this.guideLicense = guideLicense;
        this.company = company;
        this.telephone = telephone;
        this.averageRating = averageRating;
        this.rateCount = rateCount;
        this.tourRatings = tourRatings;
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.tourSessions = tourSessions;
        this.tourSessionsDate = new ArrayList<>();
        this.tourSessionsTime = new ArrayList<>();
    }

    public int getTourID() {
        return tourID;
    }

    public String getTourName() {
        return tourName;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public double getTourPrice() {
        return tourPrice;
    }

    public double getExtremeness() {
        return extremeness;
    }

    public ArrayList<Bitmap> getTourPictures() {
        return tourPictures;
    }

    public String getTourAddress() {
        return tourAddress;
    }

    public String getGuideEmail() {
        return guideEmail;
    }

    public String getGuideName() {
        return guideName;
    }

    public String getGuideLicense() {
        return guideLicense;
    }

    public String getCompany() {
        return company;
    }

    public String getTelephone() {
        return telephone;
    }

    public double getAverageRating() {

        return averageRating;
    }

    public int getRateCount() {
        return rateCount;
    }

    public ArrayList<TourSession> getTourSessions() {
        return tourSessions;
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

    public ArrayList<String> getTourReviews() {
        reviews.clear();
        for(int i = 0; i<tourRatings.size(); i++) {
            reviews.add(tourRatings.get(i).getReview());
        }
        return reviews;
    }

    public ArrayList<String> getTourSessionsDate() {
        for (int i=0; i<tourSessions.size(); i++) {
            if (!tourSessionsDate.contains(tourSessions.get(i).getSessionDay())) {
                tourSessionsDate.add(tourSessions.get(i).getSessionDay());
            }
        }
        return tourSessionsDate;
    }

    public int getTourSessionID(String date, String time) {
        for (int i=0; i<tourSessions.size(); i++) {
            if(tourSessions.get(i).getSessionDay().equals(date) && tourSessions.get(i).getSessionTime().equals(time)) {
                return tourSessions.get(i).getSessionID();
            }
        }
        return -1;
    }

    public ArrayList<String> getTourSessionsTime(String date) {
        tourSessionsTime.clear();
        for (int i=0; i<tourSessions.size(); i++) {
            if(tourSessions.get(i).getSessionDay().equals(date) && !tourSessionsTime.contains(tourSessions.get(i).getSessionTime())) {
                    tourSessionsTime.add(tourSessions.get(i).getSessionTime());
            }
        }
        return tourSessionsTime;
    }

    public ArrayList<Integer> getTourSessionAvailability(String date, String time) {
        ArrayList<Integer> tourAvailability = new ArrayList<>();
        int availabilityIndex = 0;
        for (int i=0; i<tourSessions.size(); i++) {
            if(tourSessions.get(i).getSessionDay().equals(date) && tourSessions.get(i).getSessionTime().equals(time)) {
                availabilityIndex = i;
                break;
            }
        }

        for(int i=0; i<tourSessions.get(availabilityIndex).getAvailability(); i++){
            tourAvailability.add(i+1);
        }

        return tourAvailability;
    }

}
