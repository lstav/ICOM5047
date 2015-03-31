package com.kiwiteam.nomiddleman;

import android.app.Application;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Luis on 3/13/2015.
 */
public class DatabaseConnection extends Application {


    private boolean isLogged;
    private ArrayList<TourClass> tourInformation = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> userEmail = new ArrayList<>();
    private ArrayList<String> password = new ArrayList<>();
    private ArrayList<String> userName = new ArrayList<>();
    private ArrayList<String> userLName = new ArrayList<>();
    private ArrayList<String> tourNames = new ArrayList<>();
    private ShoppingCart shoppingCart;
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

        shoppingCart = new ShoppingCart(0);

        tourInformation.add(new TourClass("Arecibo Skydiving", new ArrayList<>(Arrays.asList("Skydiving")), new String[]{"Arecibo","PR","USA"},
                200.00, new ArrayList<>(Arrays.asList("img1")), "Pepe Perez", "Best Skydiving experience", 4, new ArrayList<>(Arrays.asList("Excellent experience")),
                new ArrayList<>(Arrays.asList("Sarturday","Sunday","Friday")), new ArrayList<>(Arrays.asList("10:30 am","11:30 am","12:30 pm")), "vid1"));

        tourInformation.add(new TourClass("Ola Surf", new ArrayList<>(Arrays.asList("Surfing")), new String[]{"Isabela","PR","USA"},
                50.00, new ArrayList<>(Arrays.asList("img2")), "Pancho Rodriguez", "Prepare to surf the waves", 3, new ArrayList<>(Arrays.asList("Satisfying surf")),
                new ArrayList<>(Arrays.asList("Sunday","Monday")), new ArrayList<>(Arrays.asList("7:00 am","8:00 am")), "vid2"));

        tourInformation.add(new TourClass("Surfing Slide", new ArrayList<>(Arrays.asList("Surfing")), new String[]{"Aguadilla","PR","USA"},
                40.00, new ArrayList<>(Arrays.asList("img2")), "Jorge Garcia", "Surfing for life", 4, new ArrayList<>(Arrays.asList("Beautiful beaches")),
                new ArrayList<>(Arrays.asList("Friday","Saturday")), new ArrayList<>(Arrays.asList("8:00 am","9:00 am")), "vid3"));

    }

    public boolean isLogged() {

        return isLogged;
    }

    public ArrayList<ShoppingItem> getShoppingCart(int i) {
        return shoppingCart.getTours();
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
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int i = 0; i<tourInformation.size(); i++) {
            if (tourInformation.get(i).getTourName().toLowerCase().contains(query.toLowerCase())) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
