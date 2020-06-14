// Skeleton was based off sample code in 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/SecondActivity.java

package com.example.fotag;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class SecondActivity extends AppCompatActivity implements Observer
{
    // Private Vars
    Model model;

    /**
     * OnCreate
     * -- Called when application is initially launched.
     *
     * @param savedInstanceState
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle.html">Android LifeCycle</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(String.valueOf(R.string.DEBUG_FOTAG_ID), "SecondActivity: OnCreate");
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_second);

        // Get Model instance
        model = Model.getInstance();
        model.addObserver(this);

        // Set views
        ImageView imageView = (ImageView) findViewById(R.id.selected_item_image);
        imageView.setImageDrawable(model.getSelectedGridItem().getImageView().getDrawable());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.selected_item_rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setRatingOnSelectedGridItem(rating);
            }
        });
        ratingBar.setRating(model.getSelectedGridItem().getRating());

        // Init observers
        model.initObservers();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(String.valueOf(R.string.DEBUG_FOTAG_ID), "MainActivity: onDestory");

        // Remove observer when activity is destroyed.
        model.deleteObserver(this);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg)
    {
        Log.d(String.valueOf(R.string.DEBUG_FOTAG_ID), "View2: Update TextView");
    }
}