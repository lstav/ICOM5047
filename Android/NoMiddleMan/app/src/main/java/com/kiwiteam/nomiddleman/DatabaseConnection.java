package com.kiwiteam.nomiddleman;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Luis on 3/13/2015.
 */
public class DatabaseConnection extends Application {


    public boolean isLogged;
    public ArrayList<String> categories = new ArrayList<>();
    public ArrayList<String> userEmail = new ArrayList<>();
    public ArrayList<String> password = new ArrayList<>();
    public ArrayList<String> userName = new ArrayList<>();
    public ArrayList<String> userLName = new ArrayList<>();
    public ArrayList<String> tourNames = new ArrayList<>();
    public ArrayList<String> tourCategories = new ArrayList<>();
    public ArrayList<String> tourLocations = new ArrayList<>();
    public ArrayList<String> tourPrices = new ArrayList<>();
    public ArrayList<String> tourPictures = new ArrayList<>();
    public ArrayList<String> tourGuides = new ArrayList<>();
    public ArrayList<String> tourDescriptions = new ArrayList<>();
    public ArrayList<String> tourRating = new ArrayList<>();
    public ArrayList<String> tourReview = new ArrayList<>();
    public ArrayList<String> tourSessions = new ArrayList<>();
    public ArrayList<String> tourVideos = new ArrayList<>();
    public int index = -1;


    public void onCreate() {
        super.onCreate();
        populateLists();
    }

    private void populateLists() {
        isLogged = false;

        categories.add("Bungee Jumping");
        categories.add("Kayaking");
        categories.add("Rappelling");
        categories.add("Skydiving");
        categories.add("Surfing");

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

    public ArrayList<Integer> searchToursByCategories(String category) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        int j = 0;
        for(int i = 0; i<tourCategories.size(); i++) {
            if(tourCategories.get(i).toLowerCase().equals(category.toLowerCase())) {
                indexes.add(i);
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

    public ArrayList<String> getCategories() {
        return categories;
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

    public ArrayList<Integer> searchToursByString(String query) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        int j = 0;
        for(int i = 0; i<tourNames.size(); i++) {
            if(tourNames.get(i).toLowerCase().contains(query.toLowerCase())) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
