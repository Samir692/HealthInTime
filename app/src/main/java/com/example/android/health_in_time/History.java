package com.example.android.health_in_time;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SymbolTable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.health_in_time.database_sql.DatabaseHandler;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class History extends BaseActivity {


    private static LineChart chart;

    private static DatabaseHandler databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseHelper = new DatabaseHandler(History.this);


        chart = (LineChart) findViewById(R.id.charthistory);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);


        ArrayList<String> xVals ;//= setXAxisValues();

        ArrayList<Entry> yVals ;//= setYAxisValues();


        //List<Entry> entries = new ArrayList<Entry>();

        //entries.add(new Entry(2f, 10));
        //entries.add(new Entry(5f, 14));
        //entries.add(new Entry(7f, 28));
        //Collections.sort(entries, new EntryXComparator());
        //chart.getAxisLeft().setLabelCount(5, true);

        //System.out.println("SIZEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE = " +  entries.size());


        //ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        MyData mydata = addEntry();
        xVals = mydata.getxVals();
        yVals = mydata.getyVals();

        LineDataSet dataSet = new LineDataSet(yVals, "Rate History");
        //dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //dataSet.setValueTextColor(Color.BLUE);
        //dataSet.setColor(Color.RED);

        //List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        //dataSets.add(dataSet);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(xVals, dataSets);


        //lineData.addEntry();
        //lineData.addDataSet(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        //chart.notifyDataSetChanged();
/*
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);

        chart.setBackgroundColor(Color.LTGRAY);

        LineData linedata = new LineData();
        linedata.setValueTextColor(Color.WHITE);
        chart.setData(linedata);


        XAxis xl = chart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setDrawGridLines(true);
    **  */
        //addEntry();


        //feedMultiple();

    }

/*
    // This is used to store x-axis values
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }

   */
    private MyData addEntry() {
        /*
        LineData data = chart.getData();

        //if (data != null){
        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }


        data.addXValue("");
        */
        //data.addEntry(new Entry();

        //data.addEntry(new Entry((float) (yValue), set.getEntryCount()), 0);

        //float fl_value = (float) (Math.random() * 75) + 60f;
        //System.out.println("Shape = " + fl_value + " second = " + set.getEntryCount());


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        String username = LoginActivity.username;
        System.out.println("USERNAMEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE = " + username);
        if (username == null){
            return new MyData();
        }

        Contacts user = databaseHelper.getUser(username);
        List<UserRate> entry_data = databaseHelper.getAllRate(user);

        float[] date_time = {};
        int[] rate = {};


        System.out.println("LENGTHHHHHHHHHHHHHHHHHHHHH    " + entry_data.size());
        //float cnt1 = 0;
        int cnt = 0;
        MyData mydata = new MyData();

        for (UserRate rate_pair : entry_data) {

            String str_date = rate_pair.get_data_time();
            System.out.println("INPUTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT    " + str_date);

            //System.out.println("INPUTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTRATEEE    " + rate_pair.get_hear_rate());

            String UNIX_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
            Date formatted = null;
            try {
                formatted = formatter.parse(str_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long unixTime = (long) formatted.getTime() / 1000;

            long Xold = unixTime;

            long reference_timestamp = 1515168022;
            long Xnew = Xold - reference_timestamp;

            unixTime =  Xnew;





            System.out.println("MILLISECONDDDDDDDDDDDD    " + unixTime);

            //int date_unix = Integer.parseInt(unixTime);
            //float rate_loc = rate_pair.get_hear_rate();
            //Integer i = (int) (long) unixTime;

            //float a = cnt1;
            //int b = cnt2;
            //data.addEntry(new Entry(a, b), 0);
            //System.out.println("Date and timeeeeeeeeee   " + unixTime);
            System.out.println("Rateeeeeeeeeeeee   " + rate_pair.get_hear_rate());

            xVals.add(str_date);
            yVals.add(new Entry(rate_pair.get_hear_rate(), cnt));

            mydata.setxVals(xVals);
            mydata.setyVals(yVals);
            cnt += 1;
            //cnt2 += 1;
            System.out.println("Data addedDDDDDDDDDDDDDDDDDDDDDDD   ");

            //date_time[cnt] = date_unix;
            //rate[cnt] = rate_pair.get_hear_rate();
            //cnt += 1;

        }
        return mydata;

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(History.this , HeartRateMonitor.class);
        //finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



        /*
        // limit the number of visible entries
        chart.setVisibleXRangeMaximum(6);
        //chart.setVisibleYRange();

        chart.setData(data);
        chart.invalidate();
        //data.notifyDataChanged();

        chart.notifyDataSetChanged();
        */
       // System.out.println("Chart refreshedDDDDDDDDDDDDDDDDDDDDDDD   ");




        //System.out.println(data.getXValMaximumLength());
        //new Entry()
        //data.notifyDataChanged();

        // let the chart know it's data has changed
        //chart.notifyDataSetChanged();


        // limit the number of visible entries
        //chart.setVisibleXRangeMaximum(6);
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);

        //int VISIBLE_VALUE_RANGE = 100;
        // move to the latest entry
        //chart.moveViewToX(data.getXValCount() - 7);

        // this automatically refreshes the chart (calls invalidate())
        // mChart.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);



    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Static Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.BLUE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.BLUE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


}
