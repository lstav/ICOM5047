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
        boolean same = false;
        int index = -1;
        for (int i = 0; i<item.size(); i++) {
            if(item.get(i).equals(sItem)) {
                same = true;
                index = i;
                break;
            }
        }
        if(same) {
            this.item.get(index).addQuantity(quantity);
        } else {
            index = item.size();
            this.item.add(sItem);
        }
    }

    public ArrayList<ShoppingItem> getTours() {
        return item;
    }

    public void removeFromShoppingCart(int position) {
        item.remove(position);
    }

    public int getAccountID() {
        return accountID;
    }

    public double getTotalPrice() {
        double totPrice = 0.00;
        for (int i=0; i<item.size(); i++) {
            totPrice = totPrice + item.get(i).getTourPrice();
        }
        totalPrice = totPrice;
        return totalPrice;
    }

    public void clearShoppingCart() {
        item.clear();
    }
}
