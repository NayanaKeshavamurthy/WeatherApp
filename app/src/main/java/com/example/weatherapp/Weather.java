package com.example.weatherapp;

import java.io.Serializable;

public class Weather implements Serializable {
    public String address;
    public String country;

    public String uvIndex;
    public String icon;
    public String conditions;
    public String temperature;
    public String feelsLike;
    public String humidity;
    public String windGust;
    public String windSpeed;
    public String windDirection;
    public String visibility;
    public String cloudCover;
    public String sunrise;
    public String sunset;
    public String morningTemperature;
    public String afternoonTemperature;
    public String eveningTemperature;
    public String nightTemperature;
    public String timeZone;

    public Weather(String address, String country, String uvIndex, String icon, String conditions, String temperature, String feelsLike, String humidity, String windGust, String windSpeed, String windDirection, String visibility, String cloudCover, String sunrise, String sunset, String morningTemperature, String afternoonTemperature, String eveningTemperature, String nightTemperature, String timeZone)
    {
        this.address = address;
        this.country = country;
        this.uvIndex = uvIndex;
        this.icon = icon;
        this.conditions = conditions;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windGust = windGust;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.visibility = visibility;
        this.cloudCover = cloudCover;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.morningTemperature = morningTemperature;
        this.afternoonTemperature = afternoonTemperature;
        this.eveningTemperature = eveningTemperature;
        this.nightTemperature = nightTemperature;
        this.timeZone = timeZone;
    }

    // getters for member variables
    public String getAddress()
    {
        return address;
    }

    public String getCountry()
    {
        return country;
    }
    public String getUvIndex()
    {
        return uvIndex;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getConditions()
    {
        return conditions;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getFeelsLike()
    {
        return feelsLike;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public String getWindGust()
    {
        return windGust;
    }

    public String getWindSpeed()
    {
        return windSpeed;
    }

    public String getWindDirection()
    {
        return windDirection;
    }

    public String getVisibility()
    {
        return visibility;
    }

    public String getCloudCover()
    {
        return cloudCover;
    }

    public String getSunrise()
    {
        return sunrise;
    }

    public String getSunset()
    {
        return sunset;
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

    public String getTimeZone()
    {
        return timeZone;
    }

    // setters for member variables
    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setUvIndex(String uvIndex)
    {
        this.uvIndex = uvIndex;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public void setConditions(String conditions)
    {
        this.conditions = conditions;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public void setFeelsLike(String feelsLike)
    {
        this.feelsLike = feelsLike;
    }

    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
    }

    public void setWindGust(String windGust)
    {
        this.windGust = windGust;
    }

    public void setWindSpeed(String windSpeed)
    {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection)
    {
        this.windDirection = windDirection;
    }

    public void setVisibility(String visibility)
    {
        this.visibility = visibility;
    }

    public void setCloudCover(String cloudCover)
    {
        this.cloudCover = cloudCover;
    }

    public void setSunrise(String sunrise)
    {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset)
    {
        this.sunset = sunset;
    }

    public void setMorningTemperature(String morningTemperature)
    {
        this.morningTemperature = morningTemperature;
    }

    public void setAfternoonTemperature(String afternoonTemperature)
    {
        this.afternoonTemperature = afternoonTemperature;
    }

    public void setEveningTemperature(String eveningTemperature)
    {
        this.eveningTemperature = eveningTemperature;
    }

    public void setNightTemperature(String nightTemperature)
    {
        this.nightTemperature = nightTemperature;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }
}
