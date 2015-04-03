package com.kiwiteam.nomiddleman;

import java.util.ArrayList;

/**
 * Created by Luis on 3/26/2015.
 */
public class ShoppingCart {

    private ArrayList<ShoppingItem> item = new ArrayList<>();
    private int accountID = -1;
    private double totalPrice = 0;

    public ShoppingCart(int accountID) {
        this.accountID = accountID;
    }

    public void putTour(TourClass tour, int quantity, String date, String time) {
        ShoppingItem sItem = new ShoppingItem(tour, quantity, date, time);
        this.item.add(sItem);
        this.totalPrice = totalPrice + sItem.getTourPrice();
    }

    public ArrayList<ShoppingItem> getTours() {
        return item;
    }

    public void removeFromShoppingCart(int position) {
        this.totalPrice = this.totalPrice - item.get(position).getTourPrice();
        item.remove(position);
    }

    public int getAccountID() {
        return accountID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void clearShoppingCart() {
        item.clear();
    }
}
