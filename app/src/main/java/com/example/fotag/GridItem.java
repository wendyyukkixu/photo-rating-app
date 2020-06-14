package com.example.fotag;

import android.widget.ImageView;
import android.widget.RatingBar;

public class GridItem {
    private ImageView imageView;
    private RatingBar ratingBar;

    public GridItem(ImageView imageView, RatingBar ratingBar) {
        this.imageView = imageView;
        this.ratingBar = ratingBar;
    }

    // Setters and getters
    void setRating(float rating) {
        ratingBar.setRating(rating);
    }

    RatingBar getRatingBar() {
        return ratingBar;
    }

    float getRating() {
        return ratingBar.getRating();
    }

    ImageView getImageView() {
        return imageView;
    }

}