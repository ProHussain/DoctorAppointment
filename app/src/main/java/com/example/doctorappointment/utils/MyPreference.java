package com.example.doctorappointment.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String prefName = "Doctor";

    public MyPreference(Context context) {
        preferences = context.getSharedPreferences(prefName,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

}
