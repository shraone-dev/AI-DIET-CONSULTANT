package com.aidiet.consultant;

import android.app.Application;
import android.content.Context;

public class BMIApp extends Application {
    public static Context appContext;
    public static int option = 1;
    public static float bmi = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
