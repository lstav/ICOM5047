package com.kiwiteam.nomiddleman;

import android.app.Application;
import android.content.Intent;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Luis on 3/13/2015.
 */
public class DatabaseConnection extends Application {


    private boolean isLogged;
    private String payPalEmail = new String();
    private String payPalPass = new String();
    private ArrayList<TourSession> tourSessions = new ArrayList<>();
    private ArrayList<TourClass> tourInformation = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> userEmail = new ArrayList<>();
    private ArrayList<String> password = new ArrayList<>();
    private ArrayList<String> userName = new ArrayList<>();
    private ArrayList<String> userLName = new ArrayList<>();
    private ArrayList<String> tourNames = new ArrayList<>();
    private ArrayList<RatingClass> ratings = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private PurchaseHistory purchaseHistory = new PurchaseHistory();;
    private int index = -1;


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

        payPalEmail = "lstavarez@yahoo.com";
        payPalPass = "1234";

        shoppingCart = new ShoppingCart(0);

        tourSessions.add(new TourSession("Apr-10-15", "10:30 am", 0,true));
        tourSessions.add(new TourSession("Apr-10-15", "11:30 am", 1, true));
        tourSessions.add(new TourSession("Apr-11-15", "12:30 pm" , 2, true));
        tourSessions.add(new TourSession("Apr-9-15", "7:00 am", 3, true));
        tourSessions.add(new TourSession("Apr-9-15", "8:00 am", 4, true));
        tourSessions.add(new TourSession("Apr-10-15", "9:00 am", 5, true));
        tourSessions.add(new TourSession("Apr-11-15", "8:00 am", 6, true));
        tourSessions.add(new TourSession("Apr-18-15","8:00 am", 7, true));
        tourSessions.add(new TourSession("Apr-19-15","8:00 am", 8, true));

        ratings.add(new RatingClass(5.00,"Excellent experience"));
        ratings.add(new RatingClass(5.00,"Will come back"));
        ratings.add(new RatingClass(1.50,"Could be better"));
        ratings.add(new RatingClass(4.50,"Satisfying surf"));
        ratings.add(new RatingClass(4.00,"Beautiful beaches"));
        ratings.add(new RatingClass(4.00,"Great experience"));
        ratings.add(new RatingClass(4.00,"Great for everyone"));



        tourInformation.add(new TourClass(0, "Arecibo Skydiving", new String[]{"Arecibo","PR","USA"},
                200.00, new ArrayList<>(Arrays.asList("img1")), "Pepe Perez", "Best Skydiving experience",
                new ArrayList<>(Arrays.asList(ratings.get(0),ratings.get(1))),
                new ArrayList<>(Arrays.asList(tourSessions.get(0),tourSessions.get(1),tourSessions.get(2))), "vid1", 4.00));

        tourInformation.add(new TourClass(1, "Ola Surf", new String[]{"Isabela","PR","USA"},
                50.00, new ArrayList<>(Arrays.asList("img2")), "Pancho Rodriguez", "Prepare to surf the waves",
                new ArrayList<>(Arrays.asList(ratings.get(2), ratings.get(3))),
                new ArrayList<>(Arrays.asList(tourSessions.get(3),tourSessions.get(4),tourSessions.get(5))), "vid2", 4.00));

        tourInformation.add(new TourClass(2, "Surfing Slide", new String[]{"Aguadilla","PR","USA"},
                40.00, new ArrayList<>(Arrays.asList("img2")), "Jorge Garcia", "Surfing for life",
                new ArrayList<>(Arrays.asList(ratings.get(4),ratings.get(5),ratings.get(6))),
                new ArrayList<>(Arrays.asList(tourSessions.get(6),tourSessions.get(7))), "vid3", 3.50));

    }

    public boolean isLogged() {

        return isLogged;
    }

    public void rate(int tourID, double rating, String review) {
        int index = -1;
        for (int i=0; i<tourInformation.size(); i++) {
            if(tourInformation.get(i).getTourID() == tourID) {
                index = tourID;
            }
        }
        tourInformation.get(index).rate(rating, review);
    }

    public ArrayList<ShoppingItem> getShoppingCart(int i) {
        return shoppingCart.getTours();
    }

    public void clearShoppingCart() {
        shoppingCart.clearShoppingCart();
    }

    public double getTotalPrice() {
        return shoppingCart.getTotalPrice();
    }

    public void removeFromShoppingCart(int i) {
        shoppingCart.removeFromShoppingCart(i);
    }

    public void putToursToShoppingCart(int tourID, int quantity, String day, String time) {
        TourClass tour = getTourInformation(tourID);
        shoppingCart.putTour(tour, quantity, day, time);
    }

    public String[] getTouristInfo(int i) {
        return new String[] {userEmail.get(i), userName.get(i), userLName.get(i)};
    }

    public void setTouristFName(int i, String fName) {
        userName.set(i, fName);
    }

    public void setTouristLName(int i, String lName) {
        userLName.set(i, lName);
    }

    public void setTouristEmail(int i, String email) {
        userEmail.set(i, email);
    }

    public String getPassword(int i) {
        return password.get(i);
    }

    public void setPassword(int i, String password) {
        this.password.set(i, password);
    }

    public TourClass getTourInformation(int i) {
        return tourInformation.get(i);
    }

    public ArrayList<Integer> searchToursByCategories(String category) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i<tourInformation.size(); i++) {
            for (String s : tourInformation.get(i).getTourCategories()) {
                if(s.toLowerCase().equals(category.toLowerCase())) {
                    indexes.add(i);
                }
            }
        }
        return indexes;
    }

    public void addToHistory(String date, String time, int sessionID, int quantity, TourClass tour) {
        purchaseHistory.addToHistory(date, time, sessionID, quantity, tour);
    }

    public ArrayList<PurchaseHistory.HistoryItem> getHistory() {
        return purchaseHistory.getHistory();
    }

    public boolean login(String email, String password) {
        int index = -1;
        if(email.equals("dev")) {
            isLogged = true;
            this.index = 0;
            return true;
        }
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

    public boolean payPalLogin(String email, String password) {
        int index = -1;
        if(email.equals("dev")) {
            return true;
        }
        if(payPalEmail.equals(email) && payPalPass.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void signout() {
        this.index = -1;
        isLogged = false;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int i = 0; i<tourInformation.size(); i++) {
            if (tourInformation.get(i).getTourName().toLowerCase().contains(query.toLowerCase())) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public int getSessionID(String date, String time, int tourID) {
        int ID = -1;

        ArrayList<TourSession> sessions = tourInformation.get(tourID).getTourSessions();

        for (int i=0; i < sessions.size(); i++) {
            if(sessions.get(i).getSessionDay().equals(date) && sessions.get(i).getSessionTime().equals(time)) {
                return sessions.get(i).getSessionID();
            }
        }

        return ID;
    }

    public void setLogged(boolean bool) {
        isLogged = bool;
    }
}
