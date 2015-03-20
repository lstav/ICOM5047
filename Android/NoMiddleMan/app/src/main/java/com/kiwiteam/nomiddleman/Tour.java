package com.kiwiteam.nomiddleman;

/**
 * Created by Luis on 3/19/2015.
 */
public class Tour {
    private String name;
    private String price;
    private String picture;

    public Tour(String name, String price, String picture) {
        super();

        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

}
