package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DailyForecastViewHolder extends RecyclerView.ViewHolder
{

    TextView date, temperature, uvIndex, description, precipitation, morning, afternoon, evening, night;
    ImageView weatherIcon;

    DailyForecastViewHolder(View view)
    {
        super(view);
        date = view.findViewById(R.id.date);
        temperature = view.findViewById(R.id.temperature);
        uvIndex = view.findViewById(R.id.uvIndex);
        description = view.findViewById(R.id.description);
        precipitation = view.findViewById(R.id.precipitation);
        morning = view.findViewById(R.id.morning);
        afternoon = view.findViewById(R.id.afternoon);
        evening = view.findViewById(R.id.evening);
        night = view.findViewById(R.id.night);
        weatherIcon = view.findViewById(R.id.weatherIcon);
    }

}

