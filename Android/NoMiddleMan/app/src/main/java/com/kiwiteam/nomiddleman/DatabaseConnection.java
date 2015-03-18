package com.kiwiteam.nomiddleman;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Luis on 3/13/2015.
 */
public class DatabaseConnection extends Application {


    public boolean isLogged;
    public ArrayList<String> userEmail = new ArrayList<String>();
    public ArrayList<String> password = new ArrayList<String>();
    public ArrayList<String> userName = new ArrayList<String>();
    public ArrayList<String> userLName = new ArrayList<String>();
    public ArrayList<String> tourNames = new ArrayList<String>();
    public ArrayList<String> tourCategories = new ArrayList<String>();
    public ArrayList<String> tourLocations = new ArrayList<String>();
    public ArrayList<String> tourPrices = new ArrayList<String>();
    public ArrayList<String> tourPictures = new ArrayList<String>();
    public ArrayList<String> tourGuides = new ArrayList<String>();
    public ArrayList<String> tourDescriptions = new ArrayList<String>();
    public ArrayList<String> tourRating = new ArrayList<String>();
    public ArrayList<String> tourReview = new ArrayList<String>();
    public ArrayList<String> tourSessions = new ArrayList<String>();
    public ArrayList<String> tourVideos = new ArrayList<String>();
    public int index = -1;


    public void onCreate() {
        super.onCreate();
        isLogged = false;
        userEmail.add("luis.tavarez@upr.edu");
        password.add("1234");
        userName.add("Luis");
        userLName.add("Tavarez");

        tourNames.add("Arecibo Skydiving");
        tourNames.add("Ola Surf");
        tourNames.add("Surfing Slide");

        tourCategories.add("Skydiving");
        tourCategories.add("Surfing");
        tourCategories.add("Surfing");

        tourLocations.add("Arecibo");
        tourLocations.add("Isabela");
        tourLocations.add("Aguadilla");

        tourPrices.add("$200");
        tourPrices.add("$50");
        tourPrices.add("$40");

        tourPictures.add("img1");
        tourPictures.add("img2");
        tourPictures.add("img2");

        tourGuides.add("Pepe Perez");
        tourGuides.add("Pancho Rodriguez");
        tourGuides.add("Jorge Garcia");

        tourDescriptions.add("Best Skydiving experience");
        tourDescriptions.add("Prepare to surf the waves");
        tourDescriptions.add("Surfing for life");

        tourRating.add("4");
        tourRating.add("3");
        tourRating.add("4");

        tourReview.add("Excellent experience");
        tourReview.add("Satisfying surf");
        tourReview.add("Beautiful beaches");

        tourSessions.add("Saturday");
        tourSessions.add("Sunday");
        tourSessions.add("Friday");

        tourVideos.add("vid1");
        tourVideos.add("vid2");
        tourVideos.add("vid2");
    }

    public boolean isLogged() {

        return isLogged;
    }

    public String getEmail(int i) {

        return userEmail.get(i);
    }

    public String[] getTouristInfo(int i) {
        return new String[] {userEmail.get(i), userName.get(i), userLName.get(i)};
    }

    public String[] getTourInformation(int i) {
        return new String[]{tourNames.get(i), tourCategories.get(i), tourLocations.get(i), tourPrices.get(i), tourPictures.get(i), tourGuides.get(i), tourDescriptions.get(i),
        tourRating.get(i), tourReview.get(i), tourSessions.get(i), tourVideos.get(i)};
    }

    public int[] searchToursByCategories(String category) {
        int[] indexes = new int[]{-1, -1, -1};
        int j = 0;
        for(int i = 0; i<tourCategories.size(); i++) {
            if(tourCategories.get(i).toLowerCase().equals(category.toLowerCase())) {
                indexes[j] = i;
                j++;
            }
        }
        return indexes;
    }

    public String[] getTourNames() {
        return (String[]) tourNames.toArray();
    }

    public String[] getTourPrices() {
        return (String[]) tourPrices.toArray();
    }

    public String[] getTourRating() {
        return (String[]) tourRating.toArray();
    }

    public boolean login(String email, String password) {
        int index = -1;
        if (userEmail.contains(email)) {
            index = userEmail.indexOf(email);
        } else {
            return false;
        }
        if (email.equals(userEmail.get(index)) && password.equals(this.password.get(index))) {
            isLogged = true;
            this.index = index;
            return true;
        }
        return false;
    }

    public void signout() {
        this.index = -1;
        isLogged = false;
    }

    public int getIndex() {
        return index;
    }

    public void register(String userEmail, String password, String userName, String userLName) {
        this.userEmail.add(userEmail);
        this.password.add(password);
        this.userName.add(userName);
        this.userLName.add(userLName);
    }

}
