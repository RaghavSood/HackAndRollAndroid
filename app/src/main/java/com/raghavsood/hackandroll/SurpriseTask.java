package com.raghavsood.hackandroll;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class SurpriseTask extends AsyncTask<String, Integer, Bitmap[]> {
    public static String TAG = "SurpriseTask";
    private Context mContext;
    private ProgressDialog dialog;
    private ImageInterface imageInterface;

    public static final String[] urls =
            {"http://25.media.tumblr.com/tumblr_l0krs6h0ha1qzv52ko1_500.jpg",
                    "http://25.media.tumblr.com/Jjkybd3nSdfn7z1zUHfOSt3p_500.jpg",
                    "http://24.media.tumblr.com/tumblr_m414rlaiy01rtuomto1_500.jpg",
                    "http://25.media.tumblr.com/tumblr_m4rzhkKRYc1r6jd7fo1_500.jpg",
                    "http://25.media.tumblr.com/tumblr_lhp547MAF91qgnva2o1_500.jpg",
                    "http://25.media.tumblr.com/tumblr_m2mwiqD4Gu1qjltvho1_500.jpg",
                    "http://25.media.tumblr.com/tumblr_lmkbmnyjL71qdth8zo1_500.jpg",
                    "http://26.media.tumblr.com/tumblr_lle8o3bTqv1qjahcpo1_500.jpg",
                    "http://30.media.tumblr.com/tumblr_m0r6met4e51qbhms5o1_500.jpg",
                    "http://25.media.tumblr.com/tumblr_m4ikw59QfP1r6jd7fo1_500.jpg"};

    public SurpriseTask(Context context, ImageInterface listener) {
        mContext = context; // Save the context
        imageInterface = listener; // Save the interface instance
    }

    /**
     * This code all runs before the actual task on the UI thread
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mContext); // Create a new progress dialog
        dialog.setTitle("Downloading images");
        dialog.setIndeterminate(false); // We know exactly how many...
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Use a progress bar
        dialog.setMax(urls.length); // Set our total number of images to the max
        dialog.setCancelable(false); // Can't cancel out
        dialog.show(); // Display the dialog
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setProgress(values[0]); // Update the progress dialog
    }

    @Override
    protected Bitmap[] doInBackground(String... strings) {
        return new Bitmap[0];
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
        dialog.dismiss(); // We're done, let's get rid of this
    }
}
