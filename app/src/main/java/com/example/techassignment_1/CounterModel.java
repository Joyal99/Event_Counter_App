package com.example.techassignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CounterModel {

    private static final String PREF_NAME = "AppSettings";
    private static final String KEY_COUNTER1 = "counter1";
    private static final String KEY_COUNTER2 = "counter2";
    private static final String KEY_COUNTER3 = "counter3";
    private static final String KEY_MAX_COUNT = "maxCount";
    private static final String KEY_TOTAL_COUNT = "totalCount";
    private static final String KEY_EVENT_LIST = "eventList";

    private SharedPreferences sharedPreferences;

    // Constructor to initialize SharedPreferences
    public CounterModel(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Getting Counter Names
    public String getCounter1Name() {
        return sharedPreferences.getString(KEY_COUNTER1, "Event A");
    }

    public String getCounter2Name() {
        return sharedPreferences.getString(KEY_COUNTER2, "Event B");
    }

    public String getCounter3Name() {
        return sharedPreferences.getString(KEY_COUNTER3, "Event C");
    }

    // Setting Counter Names
    public void setCounterNames(String counter1, String counter2, String counter3) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_COUNTER1, counter1);
        editor.putString(KEY_COUNTER2, counter2);
        editor.putString(KEY_COUNTER3, counter3);
        editor.apply();
    }

    // Get and Set Maximum Count
    public int getMaxCount() {
        return sharedPreferences.getInt(KEY_MAX_COUNT, 200);
    }

    public void setMaxCount(int maxCount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MAX_COUNT, maxCount);
        editor.apply();
    }

    // Get and Update Total Count
    public int getTotalCount() {
        return sharedPreferences.getInt(KEY_TOTAL_COUNT, 0);
    }

    public void incrementTotalCount() {
        int newCount = getTotalCount() + 1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL_COUNT, newCount);
        editor.apply();
    }

    // Get and Update Event List
    public List<String> getEventList() {
        String eventString = sharedPreferences.getString(KEY_EVENT_LIST, "");
        if (eventString.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(eventString.split(",")));
    }

    // Add New Event to List
    public void addEvent(String eventName) {
        List<String> events = getEventList();
        events.add(eventName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EVENT_LIST, String.join(",", events));
        editor.apply();
    }

    // Resetting counts for Testing purposes
    public void resetCounts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL_COUNT, 0);
        editor.putString(KEY_EVENT_LIST, ""); // Clear saved events
        editor.apply();
    }


}