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
    private ArrayList<String> tourNames = new ArrayList<>();
    private ArrayList<RatingClass> ratings = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private PurchaseHistory purchaseHistory = new PurchaseHistory();;
    private int t_key = -1;


    public void onCreate() {
        super.onCreate();
        populateLists();
    }

    private void populateLists() {
        isLogged = false;

    }

    public void rate(int tourID, double rating, String review) {

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
        //TourClass tour = getTourInformation(tourID);
        //shoppingCart.putTour(tour, quantity, day, time);
    }

    public void signout() {
        this.t_key = -1;
        isLogged = false;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setLogged(boolean bool, int key) {
        setT_key(key);
        isLogged = bool;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setT_key(int key) {
        t_key = key;
    }

    public int getT_key() {
        return t_key;
    }
}
