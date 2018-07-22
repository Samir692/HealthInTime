package com.example.android.health_in_time;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import com.example.android.health_in_time.database_sql.DatabaseHandler;
import android.os.Bundle;


import java.util.concurrent.atomic.AtomicBoolean;

//import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import android.app.AlertDialog;


import android.hardware.Camera;


import android.hardware.Camera.PreviewCallback;


import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.DialogInterface;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import static android.R.attr.button;


/**
 * This class extends Activity to handle a picture preview, process the preview
 * for a red values and determine a heart beat.
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HeartRateMonitor extends BaseActivity{


    public final AppCompatActivity activity =  HeartRateMonitor.this;
    private View mLayout;
    private static View startButton;
    private static UserRate usr_rate;
    private static DatabaseHandler databaseHelper;
    private static boolean button_pressed = false;
    private static boolean camera_released = false;

    private static View buttonView;
    private static final String TAG = "HeartRateMonitor";
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static RelativeLayout RelativeLayout02;

    private static SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
     //use new camera API

    private static Camera camera = null;
    //private static View image = null;
    private static TextView text = null;
    private static LineChart chart;

    private static View Bouncing_View;
    private static WakeLock wakeLock = null;

    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];

    public static enum TYPE {
        GREEN, RED
    };

    private static TYPE currentType = TYPE.GREEN;
    public static Bundle extras;
    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    private static double beats = 0;
    private static long startTime = 0;

    /**
     * {@inheritDoc}
     */


    //public void gotAccess(){

    //}

    //public void startCamera(){
    //    showCameraPreview();
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //System.out.println("crashes1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_monitor);

        startButton =  (ImageButton) findViewById(R.id.startButton);


        preview = (SurfaceView) findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        text = (TextView) findViewById(R.id.text);
        text.setText("Place your finger");
        mLayout = findViewById(R.id.layout);
        RelativeLayout02 = (RelativeLayout) findViewById(R.id.RelativeLayout02);
        //chart = (LineChart) findViewById(R.id.chart);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //System.out.println("Crashes here");
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Tag");
        initObjects();


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(HeartRateMonitor.this);

        builder.setTitle("Exit application")
                .setMessage("Are you sure you want to exit application?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HeartRateMonitor.this , MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(false);
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    public void initObjects(){
        //extras = getIntent().getExtras();
        databaseHelper = new DatabaseHandler(activity);
        usr_rate = new UserRate();

    }

    public static void didTapButton(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(BaseActivity.getAppContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        startButton.startAnimation(myAnim);

    }

    public static void startOperation(View view) {
        button_pressed = true;
        //ViewGroup parentView = (ViewGroup) view.getParent();
        //buttonView = view;
        //parentView.removeView(view);
        didTapButton(view);

        /*


        //image = findViewById(R.id.image)

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(false);

        chart.setBackgroundColor(Color.GRAY);

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
        rightAxis.setEnabled(false);

        */
        //feedMultiple();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        camera_released = false;
        if (camera == null) {

            //try{
            //if(button_pressed == true) {
            //button_pressed = false;
            wakeLock.acquire();
            //System.out.println("What about here2");
            camera = Camera.open();
            //Camera.Parameters parameters = camera.getParameters();
            //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

           // camera.setParameters(parameters);
            //camera.startPreview();

            startTime = System.currentTimeMillis();

        }
        //remoteViews.setViewVisibility(R.id.button1, View.GONE);
        //remoteViews.setViewVisibility(R.id.button2, View.VISIBLE);
                //localAppWidgetManager.updateAppWidget(componentName, remoteViews);
           // }
        //} catch(Exception e) {
        //    Log.e("Error", ""+e);
       // }
        //System.out.println("What about here");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        if (!camera_released) {

            //System.out.println("BUTTON IS NOT REALASEDDDDDDDDDDDDDDDDDDDDDD");
            wakeLock.release();

            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private static PreviewCallback previewCallback = new PreviewCallback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null) throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            // Log.i(TAG, "imgAvg="+imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    // Log.d(TAG, "BEAT!! beats="+beats);
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize) averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
                //image.postInvalidate();
            }

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 10) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180) {
                    startTime = System.currentTimeMillis();
                    beats = 0;
                    processing.set(false);
                    return;
                }

                // Log.d(TAG,
                // "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

                if (beatsIndex == beatsArraySize) beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);

                String extra_mess = LoginActivity.username;
                /*
                if(extras == null) {
                    extra_mess= null;
                } else {
                    extra_mess= extras.getString("com.example.android.health_in_time");
                }
                */
                Contacts user_local = databaseHelper.getUser(extra_mess);
                usr_rate.set_id(user_local.get_id());
                usr_rate.set_hear_rate(beatsAvg);

                text.setText("Succesfull");
                stopOperation(beatsAvg);

                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }
    };


    public static void stopOperation(int record){

        if (camera != null) {
            if (wakeLock.isHeld()) {
                Log.v(TAG, "Releasing wakelock");
                try {
                    wakeLock.release();
                    Camera.Parameters p = camera.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    camera_released = true;

                    if (Bouncing_View != null){
                        Bouncing_View.clearAnimation();
                    }

                } catch (Throwable th) {
                    throw th;
                    // ignoring this exception, probably wakeLock was already released
                }
            } else {
                // should never happen during normal workflow
                Log.e(TAG, "Wakelock reference is null");
            }


        }
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(BaseActivity.getAppContext());

        String suggest = "";

        if (50 <= record && record <= 100){
            suggest = "You are doing perfect, KEEP GOING!!!!";
        }
        else if(record < 50){
            suggest = "Your heart rate is low, STOP WHAT YOU ARE DOING NOW AND WALK!!";
        }
        else{
            suggest = "Your heart rate is high, TAKE A DEEP BREATH FOR ONE MINUTE";
        }


        builder.setTitle("Save record")
                .setMessage("Your heart rate is " + record + "\n" + suggest)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //save record
                        databaseHelper.addRate(usr_rate);
                        BaseActivity.restartgetAppContext();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BaseActivity.restartgetAppContext();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
    /*
    public void addEntry(){
        LineData data = chart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null){
            set = createSet();
            System.out.println("Dataset is initiatied");
            data.addDataSet(set);
        }

        data.addXValue("");
        data.addEntry(new Entry(5f, 0), 0);
        data.addEntry(new Entry(15f, 1), 2);
        data.addEntry(new Entry(3f, 2), 3);
        data.addEntry(new Entry(10f, 3), 1);

        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
*/
/*
    public void addEntry(){
        List<Entry> valsComp1 = new ArrayList<Entry>();
        List<Entry> valsComp2 = new ArrayList<Entry>();

        //Entry c1e1 = ; // 0 == quarter 1
        valsComp1.add(new Entry(5f, 0));
        //Entry c1e2 = new Entry((float) 3, 14); // 1 == quarter 2 ...
        valsComp1.add(new Entry(15f, 1));
        // and so on ...

        //Entry c2e1 = new Entry((float) 5, 13); // 0 == quarter 1
        valsComp2.add(new Entry(3f, 2));
        //Entry c2e2 = new Entry((float) 8, 11); // 1 == quarter 2 ...
        valsComp2.add(new Entry(10f, 3));

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        final String[] xValues = new String[] { "Company 1", "Company 2" };

        LineData data = new LineData(xValues, dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh
    }
*/



    private void addEntry(){
        LineData data = chart.getData();

        //if (data != null){
        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null){
            set = createSet();
            data.addDataSet(set);
        }



        data.addXValue("");
        //data.addEntry(new Entry();

        //data.addEntry(new Entry((float) (yValue), set.getEntryCount()), 0);

        float fl_value = (float)(Math.random() * 75) + 60f;
        //System.out.println("Shape = " + fl_value + " second = " + set.getEntryCount());
        data.addEntry(new Entry(fl_value, set.getEntryCount()), 0);
        //System.out.println(data.getXValMaximumLength());
        //new Entry()
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();


        // limit the number of visible entries
        chart.setVisibleXRangeMaximum(6);
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);

        //int VISIBLE_VALUE_RANGE = 100;
        // move to the latest entry
        chart.moveViewToX(data.getXValCount() - 7);

        // this automatically refreshes the chart (calls invalidate())
        // mChart.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);
        }




    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
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
/*
    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i ++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    //pause between adds
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
*/
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        /**
         * {@inheritDoc}
         */


        @Override
        public void surfaceCreated(SurfaceHolder holder) {

                //boolean clicked=false;

                //my button clic
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        try {
                            camera.setPreviewDisplay(previewHolder);
                            camera.setPreviewCallback(previewCallback);
                            text.setText("Calculating.");
                            //button_pressed = true;
                            Bouncing_View = v;
                            startOperation(v);
                        } catch (Throwable t) {
                            Log.e("PreviewDemo-surfk", "Exception in setPreviewDisplay()", t);
                        }
                    }
                });




        }


        /**
         * {@inheritDoc}
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                //Log.d(TAG, "Using width=" + size.width + " height=" + size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }


        private  Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
            Camera.Size result = null;

            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                if (size.width <= width && size.height <= height) {
                    if (result == null) {
                        result = size;
                    } else {
                        int resultArea = result.width * result.height;
                        int newArea = size.width * size.height;

                        if (newArea < resultArea) result = size;
                    }
                }
            }

            return result;
        }
    };
}