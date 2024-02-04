package com.example.weatherapp;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder
{
        TextView day,time ,temp,desc;
        ImageView icon;
        HourlyWeatherViewHolder(View view)
        {
            super(view);
            day = view.findViewById(R.id.recyclerHourDay);
            time = view.findViewById(R.id.recyclerHourTime);
            temp = view.findViewById(R.id.recyclerHourTemp);
            desc = view.findViewById(R.id.recyclerHourDesc);
            icon = view.findViewById(R.id.recyclerHourIcon);

        }
}
