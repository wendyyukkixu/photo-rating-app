// Skeleton was based off sample code in 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/MainActivity.java

package com.example.fotag;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    // Private Vars
    Model model;

    // Toolbar references
    ImageView toolbar_load_image_view;
    ImageView toolbar_load_image_set_view;
    ImageView toolbar_clear_images_view;
    RatingBar toolbar_rating_bar_filter;

    // Grid references
    GridLayout gridLayout;
    ScrollView scrollView;
    String[] image_set_urls = {
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/bunny.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/chinchilla.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/deer.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/doggo.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/ducks.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/fox.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/hamster.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/hedgehog.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/husky.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/kitten.png",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/loris.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/puppy.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/running.jpg",
            "https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/sleepy.png"};
    String image_url = "";

    /**
     * OnCreate
     * -- Called when application is initially launched.
     *    @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle.html">Android LifeCycle</a>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(String.valueOf(R.string.DEBUG_FOTAG_ID), "MainActivity: OnCreate");
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Model instance
        model = Model.getInstance();
        model.addObserver(this);

        // Get toolbar references
        toolbar_load_image_view = (ImageView) findViewById(R.id.toolbar_load_image);
        toolbar_load_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(v);
            }
        });
        toolbar_load_image_set_view = (ImageView) findViewById(R.id.toolbar_load_image_set);
        toolbar_load_image_set_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageSet(v);
            }
        });
        toolbar_clear_images_view = (ImageView) findViewById(R.id.toolbar_clear_images);
        toolbar_clear_images_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImages(v);
            }
        });
        toolbar_rating_bar_filter = (RatingBar) findViewById(R.id.toolbar_rating_bar);
        toolbar_rating_bar_filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                filterImages(rating);
            }
        });

        // Get gridLayout reference
        gridLayout = (GridLayout) findViewById(R.id.gridlayout);
        scrollView = (ScrollView) findViewById(R.id.scrollview);

        // Init observers
        model.initObservers();
    }

    // Saves scroll position upon orientation change
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("scroll_position", new int[]{scrollView.getScrollY()});

        // Scroll position will be double from landscape (2 images per row) to portait (1 image per row)
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            outState.putIntArray("scroll_position", new int[]{scrollView.getScrollY()*2});
        }
    }

    // Loads scroll position upon orientation change
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("scroll_position");
        if(position != null)
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(0, position[0]);

                    // Scroll position will be halved from portrait (1 image per row) to landscape (2 images per row)
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        scrollView.scrollTo(0, position[0]/2);
                    }
                }
            });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(String.valueOf(R.string.DEBUG_FOTAG_ID), "MainActivity: onDestroy");

        // Remove observer when activity is destroyed.
        model.deleteObserver(this);
    }

    // Asynchronously fetches a png or jpg image from a URL and returns a corresponding GridItem with no rating
    // If the URL points to an invalid image, an error is displayed
    public GridItem makeGridItem(String url){
        // Image View set up
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams image_layout_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image_layout_params.gravity = Gravity.CENTER;
        imageView.setLayoutParams(image_layout_params);
        imageView.setAdjustViewBounds(true);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageView, this);
        downloadImageTask.execute(url);
        // TODO: CHECK ERROR

        // Rating Bar set up
        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setNumStars(5);
        LinearLayout.LayoutParams rating_bar_layout_params = new LinearLayout.LayoutParams(650, 200);
        rating_bar_layout_params.gravity = Gravity.CENTER;
        ratingBar.setLayoutParams(rating_bar_layout_params);
        ratingBar.setPadding(0,15,0,0);

        // Create the GridItem
        final GridItem gridItem = new GridItem(imageView, ratingBar);
        final Context context = this;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelectedGridItem(gridItem);
                // Create Intent to launch second activity
                Intent intent = new Intent(context, SecondActivity.class);

                // Start activity
                context.startActivity(intent);
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.filterGridItems();
            }
        });

        // Add image button and rating bar to model
        if (!model.getGridItems().contains(gridItem)) {
            model.insertGridItem(gridItem, model.getGridItems().size());
        }
        return gridItem;
    }

    // Adds the GridItem to the view (added to grid layout)
    public void displayGridItem(GridItem gridItem) {
        ImageView imageView = gridItem.getImageView();
        RatingBar ratingBar = gridItem.getRatingBar();

        // Pixel 3 dimensions: 1080 x 2160
        // LinearLayout set up
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(1014, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(10,20,10,15);

        // FrameLayout set up (used to constrain image)
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams frame_layout_params = new FrameLayout.LayoutParams(1080, 680);
        frame_layout_params.gravity = Gravity.CENTER;
        frameLayout.setLayoutParams(frame_layout_params);

        // Add views to layouts
        if (imageView.getParent() != null) {
            ((ViewGroup)imageView.getParent()).removeView(imageView);
        }
        frameLayout.addView(imageView);
        if (frameLayout.getParent() != null) {
            ((ViewGroup)frameLayout.getParent()).removeView(frameLayout);
        }
        linearLayout.addView(frameLayout);
        if (ratingBar.getParent() != null) {
            ((ViewGroup)ratingBar.getParent()).removeView(ratingBar);
        }
        linearLayout.addView(ratingBar);

        // Add linear layout containing image button and rating bar to grid layout
        gridLayout.addView(linearLayout);
    }

    // Called when user taps the loadImage button, prompts user to enter a URL to fetch an image
    public void loadImage(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Load Image");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                image_url = input.getText().toString();
                if (!image_url.equals("")) {
                    GridItem gridItem = makeGridItem(image_url);
                    displayGridItem(gridItem);
                    redrawGridLayout();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Called when user taps the loadImage button, loads a large set of images over the network from cs349 URL
    public void loadImageSet(View v) {
        for (String url : image_set_urls) {
            GridItem gridItem = makeGridItem(url);
            displayGridItem(gridItem);
            redrawGridLayout();
        }
    }

    // Called when user taps the clearImages button, clears all images in GridLayout
    public void clearImages(View v) {
        model.clearGridItems();
    }

    // Called when user updates the filter rating bar, filters corresponding images in GridLayout
    public void filterImages(float rating) {
        model.setFilter(rating);
    }

    // Redraws entire GridLayout according to the filter
    public void redrawGridLayout() {
        // Remove all views from layouts so that they can be readded to recreated layouts
        int count = gridLayout.getChildCount();
        for (int i = 0 ; i < count ; i++){
            if (i%2 == 0) {
                LinearLayout linear_child = (LinearLayout)gridLayout.getChildAt(i);
                FrameLayout frame_child = (FrameLayout)linear_child.getChildAt(0);

                frame_child.removeAllViews();
                linear_child.removeAllViews();
            }
        }
        gridLayout.removeAllViews();

        // Redraw filtered grid items
        for (GridItem gridItem : model.getFilteredGridItems()) {
            displayGridItem(gridItem);
        }
    }

    // Called when user enters a URL that does not point to a valid image, shows error alert and deletes the corresponding gridItem
    public void imageURLError(final ImageView imageView){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image Load Error");
        builder.setMessage("Entered URL does not point to a valid image.");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                model.deleteGridItem(imageView);
            }
        });
        builder.show();
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
        redrawGridLayout();
    }
}
