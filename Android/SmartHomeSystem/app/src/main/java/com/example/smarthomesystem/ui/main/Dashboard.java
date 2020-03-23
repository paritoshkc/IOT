package com.example.smarthomesystem.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smarthomesystem.R;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

public class Dashboard extends Fragment {

    Time time;
    Handler handler;
    Runnable runnable;
    View v;
    TextView dateString;
    TextView timeString;
    TextView dateDisplay;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dateString = v.findViewById(R.id.dateString);
        timeString = v.findViewById(R.id.timeString);
        dateDisplay = v.findViewById(R.id.dateDisplay);

        time  = new Time();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                time.setToNow();
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Regular.ttf");
                dateString.setText(String.format("%02d:%02d:%02d",time.hour,time.minute,time.second));
                timeString.setText("Battery: "+getBatteryLevel()+"%");
                dateDisplay.setText("Date: "+weekDay(time.weekDay)+" "+time.monthDay +"-"+time.month+"-"+time.year);
                dateString.setTypeface(tf);
                timeString.setTypeface(tf);
                dateDisplay.setTypeface(tf);

                handler.postDelayed(this,1000);

            }

        };
        handler.postDelayed(runnable,1000);
        return v;
    }

    public float getBatteryLevel(){
        Intent batteryIntent = getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

        if(level==-1|| scale == -1)
        {
            return 50.0f;
        }
        return ((float)level/(float)scale)*100.0f;
    }

    public String weekDay(int dayWeek)
    {
        switch (dayWeek)
        {
            case 1 : return("MON");
            case 2 : return("TUES");
            case 3 : return("WED");
            case 4 : return("THURS");
            case 5 : return("FRI");
            case 6 : return("SAT");
            case 7 : return("SUN");
            default : return("Invalid week");
        }
    }


    public class DrawingView extends View{

        Paint background,text,textPaintBack;

        Typeface tf;

        int hours,minutes,seconds,weekday,date;
        float battery;

        public DrawingView(Context context, int hours, int minutes, int seconds, int weekday, int date, float battery) {
            super(context);
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.weekday = weekday;
            this.date = date;
            this.battery = battery;



            background = new Paint();
            background.setColor(ContextCompat.getColor(getContext(),R.color.background));

            text = new Paint();
            text.setColor(ContextCompat.getColor(getContext(),R.color.text));
            text.setAntiAlias(true);
            text.setTextAlign(Paint.Align.CENTER);
            text.setTextSize(getResources().getDimension(R.dimen.text_size));
            text.setTypeface(tf);

            textPaintBack = new Paint();
            textPaintBack.setColor(ContextCompat.getColor(getContext(),R.color.text_back));
            textPaintBack.setAntiAlias(true);
            textPaintBack.setTextAlign(Paint.Align.CENTER);
            textPaintBack.setTextSize(getResources().getDimension(R.dimen.text_size));
            textPaintBack.setTypeface(tf);

        }
    }
}
