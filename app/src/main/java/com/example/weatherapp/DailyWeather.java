package com.example.weatherapp;

import java.io.Serializable;

public class DailyWeather implements Serializable
{
    public String date;

    public String precipProb;
    public String uvIndex;
    public String desc;
    public String icon;
    public String temperature;

    public String morningTemperature;
    public String afternoonTemperature;
    public String eveningTemperature;
    public String nightTemperature;
    public boolean fDegree;

    DailyWeather(String date, String precipProb, String uvIndex, String desc, String icon, String temperature,
                 String morningTemperature, String afternoonTemperature, String eveningTemperature, String nightTemperature, boolean fDegree)
    {
        this.date = date;
        this.precipProb = precipProb;
        this.uvIndex = uvIndex;
        this.desc = desc;
        this.icon = icon;
        this.temperature = temperature;
        this.morningTemperature = morningTemperature;
        this.afternoonTemperature = afternoonTemperature;
        this.eveningTemperature = eveningTemperature;
        this.nightTemperature = nightTemperature;
        this.fDegree = fDegree;
    }

    public String getDate()
    {
        return date;
    }

   public String getPrecipProb()
    {
        return precipProb;
    }

    public String getUvIndex()
    {
        return uvIndex;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getMorningTemperature()
    {
        return morningTemperature;
    }

    public String getAfternoonTemperature()
    {
        return afternoonTemperature;
    }

    public String getEveningTemperature()
    {
        return eveningTemperature;
    }

    public String getNightTemperature()
    {
        return nightTemperature;
    }
}
