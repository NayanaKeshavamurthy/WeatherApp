package com.example.weatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyForecast extends AppCompatActivity
{
    RecyclerView dataView;
    String jsonData;
    boolean scaleUsed;
    ArrayList<DailyWeather> dailyWeathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        dataView = findViewById(R.id.dataView);

        ActionBar actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF786A50"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        retrieveData();
    }
    @SuppressLint("SetTextI18n")
    public void retrieveData()
    {

        jsonData = getIntent().getStringExtra("dailyData");
        scaleUsed = getIntent().getBooleanExtra("scaleSelection",true);
        String tempScale = (scaleUsed ? "F" : "C");

        try
        {
            // get the days data from the json
            JSONObject jsonDays = new JSONObject(jsonData);

            // set title rom json
            setTitle(jsonDays.getString("address"));

            // parse the data for all the days in api
            JSONArray arrDays = jsonDays.getJSONArray("days");
            for(int i = 0 ; i < 15; i++)
            {
                JSONObject dateObject = arrDays.getJSONObject(i);

                String iconCode = dateObject.getString("icon");
                iconCode = iconCode.replace("-", "_");

                String description = dateObject.getString("description");
                String precipitation = dateObject.getString("precipprob") + "% precip.";
                String uvi = dateObject.getString("uvindex");

                JSONArray arrHours = dateObject.getJSONArray("hours");
                String morning = arrHours.getJSONObject(8).getString("temp");
                String afternoon = arrHours.getJSONObject(13).getString("temp");
                String evening = arrHours.getJSONObject(17).getString("temp");
                String night = arrHours.getJSONObject(arrHours.length()-1).getString("temp");


                SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());
                long lngDateTime = dateObject.getLong("datetimeEpoch");
                Date dateTime = new Date(lngDateTime * 1000);
                String date = dayDate.format(dateTime);

                String temperature = String.format("%.0f° " + tempScale, Double.parseDouble(dateObject.getString("tempmax")))
                        + " / " + String.format("%.0f° " + tempScale, Double.parseDouble(dateObject.getString("tempmin")));


                dailyWeathers.add(new DailyWeather(date, precipitation, uvi, description, iconCode, temperature, morning,
                        afternoon, evening, night, scaleUsed));
                setRCViewList();
            }

        }
        catch (Exception e)
        {
            Log.d("Error",""+e.getMessage());
        }
    }
    void setRCViewList()
    {
        // call the adapter for setting the controls
        DailyForecastAdapter dailyAdapter = new DailyForecastAdapter(dailyWeathers,DailyForecast.this);
        dataView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dataView.setAdapter(dailyAdapter);
    }
}