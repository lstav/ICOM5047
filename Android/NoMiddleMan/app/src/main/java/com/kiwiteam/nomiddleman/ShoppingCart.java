package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

/**
 * Created by Luis on 3/26/2015.
 */
public class ShoppingCart {

    private ArrayList<Integer> tourID = new ArrayList<>();
    private int accountID = -1;

    public ShoppingCart(int accountID) {
        this.accountID = accountID;
    }

    public void putTour(Integer tourID) {
        this.tourID.add(tourID);
    }

    public ArrayList<Integer> getTours() {
        return tourID;
    }

    public int getAccountID() {
        return accountID;
    }

}
