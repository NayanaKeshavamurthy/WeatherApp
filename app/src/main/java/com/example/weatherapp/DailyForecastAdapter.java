package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastViewHolder>
{
    public final List<DailyWeather> dailyWeather;
    public final DailyForecast mainAct;

    public DailyForecastAdapter(List<DailyWeather> dailyWeather, DailyForecast ma)
    {
        this.dailyWeather = dailyWeather;
        mainAct = ma;
    }

    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dailyforecastview, parent, false);


        return new DailyForecastViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position)
    {

        DailyWeather daily = dailyWeather.get(position);

        String scaleUsed = (daily.fDegree ? "F" : "C");

        holder.date.setText(daily.getDate());
        holder.temperature.setText(daily.getTemperature());
        holder.uvIndex.setText("UV Index : "+daily.getUvIndex());
        holder.description.setText(daily.getDesc());
        holder.precipitation.setText("( "+daily.getPrecipProb()+" )");
        holder.morning.setText(String.format("%.0f° " + scaleUsed, Double.parseDouble(daily.getMorningTemperature())));
        holder.afternoon.setText(String.format("%.0f° " + scaleUsed, Double.parseDouble(daily.getAfternoonTemperature())));
        holder.evening.setText(String.format("%.0f° " + scaleUsed, Double.parseDouble(daily.getEveningTemperature())));
        if(!daily.getNightTemperature().equals(""))
          holder.night.setText(String.format("%.0f° " + scaleUsed, Double.parseDouble(daily.getNightTemperature())));

        holder.date.setText(daily.getDate());
        holder.weatherIcon.setImageResource(mainAct.getResources().getIdentifier(daily.icon, "drawable", mainAct.getPackageName()));

    }

    @Override
    public int getItemCount()
    {
        return dailyWeather.size();
    }
}
