package com.kiwiteam.nomiddleman;

import android.media.Rating;

/**
 * Created by Luis on 4/3/2015.
 */
public class RatingClass {
    private double rating;
    private String review;

    public RatingClass() {
        rating = 0;
        review = "";
    }

    public RatingClass (double rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
