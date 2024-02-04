package com.example.weatherapp;

import java.io.Serializable;

public class HourlyWeather implements Serializable
{
    public String day;
    public String timeFormatted;
    public String icon;
    public String temperature;
    public String description;
    public String timeEpoch;

    public HourlyWeather(String formattedTime, String day, String description, String timeEpoch, String imageIcon, String temperature)
    {
        this.day = day;
        this.timeFormatted = formattedTime;
        this.icon = imageIcon;
        this.temperature = temperature;
        this.description = description;
        this.timeEpoch = timeEpoch;
    }
}


