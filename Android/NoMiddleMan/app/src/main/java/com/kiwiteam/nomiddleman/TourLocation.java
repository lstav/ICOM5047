package com.kiwiteam.nomiddleman;

/**
 * Created by Luis on 4/28/2015.
 */
public class TourLocation {
    private String country;
    private String state;
    private String city;
    private int id;

    public TourLocation() {
        country = "Any";
        state = "Any";
        city = "Any";
        id = -1;
    }

    public TourLocation(int id, String country, String state, String city) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public int getId() {
        return id;
    }
}
