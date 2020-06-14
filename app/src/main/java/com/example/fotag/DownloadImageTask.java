// Skeleton was based off code from https://stackoverflow.com/questions/5776851/load-image-from-url

package com.example.fotag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    MainActivity mainActivity;

    public DownloadImageTask(ImageView bmImage, MainActivity mainActivity) {
        this.imageView = bmImage;
        this.mainActivity = mainActivity;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        }

        // Display error alert if URL did not point to a valid image.
        else {
            mainActivity.imageURLError(imageView);
        }
    }
}



