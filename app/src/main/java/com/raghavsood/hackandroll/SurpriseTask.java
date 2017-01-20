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

    public SurpriseTask(Context context, ImageInterface listener) {
        mContext = context; // Save the context
        imageInterface = listener; // Save the interface instance
    }

    @Override
    protected Bitmap[] doInBackground(String... strings) {
        return new Bitmap[0];
    }
}
