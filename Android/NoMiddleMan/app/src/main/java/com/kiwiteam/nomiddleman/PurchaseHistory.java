package com.kiwiteam.nomiddleman;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Luis on 4/1/2015.
 */
public class PurchaseHistory {
    private ArrayList<HistoryItem> item;

    public PurchaseHistory() {
        item = new ArrayList<>();
    }

    public void addToHistory(String date, String time, int sessionID, int quantity, TourClass tour) {
        item.add(new HistoryItem(date, time, sessionID, quantity, tour));
    }

    public ArrayList<HistoryItem> getHistory() {
        return item;
    }

    public class HistoryItem {
        private String date;
        private String time;
        private int sessionID;
        private int quantity;
        private TourClass tour;

        public HistoryItem() {
            date = new String();
            time = new String();
            sessionID = -1;
            quantity = 0;
            tour = new TourClass();
        }

        public HistoryItem(String date, String time, int sessionID, int quantity, TourClass tour) {
            this.date = date;
            this.time = time;
            this.sessionID = sessionID;
            this.quantity = quantity;
            this.tour = tour;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public TourClass getTour() {
            return tour;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getSessionID() {
            return sessionID;
        }

        public double getPrice() {
            return tour.getTourPrice()*quantity;
        }
    }
}
