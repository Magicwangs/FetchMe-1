package com.cgf.fetchme.activity;

import android.app.Application;
import android.util.Log;

/**
 * Created by chenguanfu on 3/28/2016.
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    static {
        try {
            Log.d(TAG, "load library----in----");
            System.loadLibrary("caffe");
            System.loadLibrary("caffe_jni");
            Log.d(TAG, "load library----out----");
        } catch (UnsatisfiedLinkError ule) {
            Log.d(TAG, ule.toString());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MainApplication----in----");
        // add some init before the activity like so resources
        Log.d(TAG, "MainApplication----out----");
    }
}
