package com.example.android.health_in_time;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by samir692 on 1/5/18.
 */

public class MyData {

    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<Entry> yVals = new ArrayList<Entry>();

    public MyData() {
    }

    public void setxVals(ArrayList<String> xVals) {
        this.xVals = xVals;
    }

    public void setyVals(ArrayList<Entry> yVals) {
        this.yVals = yVals;
    }

    public ArrayList<String> getxVals() {
        return xVals;
    }

    public ArrayList<Entry> getyVals() {
        return yVals;
    }
}
