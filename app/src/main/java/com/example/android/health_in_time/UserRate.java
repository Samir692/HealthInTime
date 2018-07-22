package com.example.android.health_in_time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by samir692 on 12/4/17.
 */

public class UserRate {

    private int _id;
    private int _hear_rate;
    private String _data_time;

    public UserRate() {
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_hear_rate() {
        return _hear_rate;
    }

    public void set_hear_rate(int _hear_rate) {
        this._hear_rate = _hear_rate;
    }

    public String get_data_time() {
        return _data_time;
    }

    public void set_data_time(String _data_time) {
        this._data_time = _data_time;
    }
}
