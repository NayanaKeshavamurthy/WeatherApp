package com.example.weatherapp;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherViewHolder>
{
    private final List<HourlyWeather> hourlyWeatherList;
    private final MainActivity mainActivity;

    public HourlyWeatherAdapter(List<HourlyWeather> hourlyWeatherList, MainActivity ma)
    {
        this.hourlyWeatherList = hourlyWeatherList;
        mainActivity = ma;
    }

    @NonNull
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourlyview, parent, false);
        return new HourlyWeatherViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position)
    {

        HourlyWeather hourlyWeather = hourlyWeatherList.get(position);

        holder.time.setText(hourlyWeather.timeFormatted);
        holder.temp.setText(hourlyWeather.temperature);
        holder.desc.setText(hourlyWeather.description);
        holder.day.setText(hourlyWeather.day);
        holder.icon.setImageResource(mainActivity.getResources().getIdentifier(hourlyWeather.icon, "drawable", mainActivity.getPackageName()));
    }


    public int getItemCount()
    {
        return hourlyWeatherList.size();
    }

}
