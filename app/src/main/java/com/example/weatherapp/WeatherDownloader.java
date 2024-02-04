package com.example.weatherapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherDownloader
{
    private static MainActivity mainActivity;
    private static boolean scaleF;

    private static final String weatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String yourAPIKey = "4RHVD8YNYXLL8US6JFW3LEDW6";

    public static void downloadWeather(MainActivity mainActivityIn, String location, boolean fahrenheit, SharedPreferences sf)
    {

        mainActivity = mainActivityIn;

        scaleF = fahrenheit;

        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        try
        {
            URL site = new URL(weatherURL + location + "?unitGroup=" + (fahrenheit? "us" : "metric")+ "&key=" + yourAPIKey);
            Uri.Builder buildURL = Uri.parse(String.valueOf(site)).buildUpon();

            String urlToUse = buildURL.build().toString();

            Response.Listener<JSONObject> listener =
                    response->{
                        sf.edit().putString("location", location).apply();
                        sf.edit().putBoolean("scaleSelection",fahrenheit).apply();
                        parseJSON(response.toString());
                      };


            Response.ErrorListener error =
                    error1 -> {
                        mainActivity.callToast();
            };

            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, urlToUse,
                            null, listener, error);

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void parseJSON(String s)
    {

        try
        {
            Weather weather;
            // parse and set the data required and construct a weather object
            JSONObject jObjMain = new JSONObject(s);

            // "weather" section
            JSONArray arrObjDays = jObjMain.getJSONArray("days");
            JSONObject objCurrentCondition = (JSONObject) jObjMain.getJSONObject("currentConditions");
            JSONObject objDays = (JSONObject) arrObjDays.get(0);

            //weather_icon
            String icon = objCurrentCondition.getString("icon");
            icon = icon.replace("-", "_"); // Replace all dashes with underscores

            //UV Index
            String uvIndex = objCurrentCondition.getString("uvindex");

            //conditions
            String conditions= objCurrentCondition.getString("conditions");

            //temperature
            String temperature = objCurrentCondition.getString("temp");

            //feelsLike
            String feelsLike = objCurrentCondition.getString("feelslike");

            //humidity
            String humidity = objCurrentCondition.getString("humidity");

            //wind data
            String windSpeed = objCurrentCondition.getString("windspeed");
            String windDir = objCurrentCondition.getString("winddir");
            String windGust = objCurrentCondition.getString("windgust");

            //visibilty
            String visibility = objCurrentCondition.getString("visibility");

            //cloudCover
            String cloudcover = objCurrentCondition.getString("cloudcover");

            long lngDateTime = objCurrentCondition.getLong("datetimeEpoch");
            Date dateTime = new Date(lngDateTime * 1000);

            // formatter for date and time
            SimpleDateFormat fullDate = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
            SimpleDateFormat timeOnly24hr = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());

            // Date - format the date epoch obtained from json to display
            String dateTimeF = fullDate.format(dateTime);

            String currHour = timeOnly24hr.format(dateTime);

            // Sunrise Time - format the sunrise date epoch obtained from json to display
            long sunrise = objCurrentCondition.getLong("sunriseEpoch");
            Date dateTime2 = new Date(sunrise * 1000);
            String strSunrise =  timeOnly.format(dateTime2);


            // Sunset Time - format the sunrise date epoch obtained from json to display
            long sunset = objCurrentCondition.getLong("sunsetEpoch");
            Date dateTime3 = new Date(sunset * 1000);
            String strSunset = timeOnly.format(dateTime3);

            String morningTemp, afternoonTemp, eveningTemp, nightTemp;

            JSONArray arrHours = objDays.getJSONArray("hours");
            morningTemp = arrHours.getJSONObject(8).getString("temp");
            afternoonTemp = arrHours.getJSONObject(13).getString("temp");
            eveningTemp = arrHours.getJSONObject(17).getString("temp");
            nightTemp = arrHours.getJSONObject(23).getString("temp");

            ArrayList<HourlyWeather> arrObjDataHourly = new ArrayList<>();

            int hour = Integer.parseInt(currHour.substring(0,2));
            String strDay, strTime, hourIcon, hourTemp, hourDesc;

            for(int i = hour+1 ; i<24; i++)
            {
                strDay = "Today";
                hourTemp = String.format("%.0f° " + (scaleF ? "F" : "C"), Double.parseDouble(arrObjDays.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("temp")));
                strTime = (i <= 12 ? String.valueOf(i) : String.valueOf(i-12)) + ":00" + (i < 12 ? "AM" : "PM");
                hourDesc = arrObjDays.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("conditions");
                hourIcon = arrObjDays.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("icon");
                hourIcon = hourIcon.replace("-", "_");
                arrObjDataHourly.add(new HourlyWeather(strTime, strDay, hourDesc, arrObjDays.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("datetimeEpoch"), hourIcon, hourTemp));

            }
            for(int i = 1 ; i<4; i++)
            {
                for (int j = 0; j < 24; j++) {
                    long datetimeepcoh = arrObjDays.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getLong("datetimeEpoch");
                    SimpleDateFormat dayDateh = new SimpleDateFormat("EEEE", Locale.getDefault());
                    Date dateTimeh = new Date(datetimeepcoh * 1000);
                    strDay = dayDateh.format(dateTimeh);

                    hourTemp = String.format("%.0f° " + (scaleF ? "F" : "C"), Double.parseDouble(arrObjDays.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("temp")));
                    strTime = (j <= 12 ? String.valueOf(j) : String.valueOf(j-12)) + ":00" + (j < 12 ? "AM" : "PM");
                    hourDesc = arrObjDays.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("conditions");
                    hourIcon = arrObjDays.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("icon");
                    hourIcon = hourIcon.replace("-", "_");
                    arrObjDataHourly.add(new HourlyWeather(strTime, strDay, hourDesc, arrObjDays.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("datetimeEpoch"),hourIcon, hourTemp));
                }
            }


            weather = new Weather("", "", uvIndex, icon, conditions, temperature, feelsLike, humidity, windGust
                    , windSpeed, windDir, visibility, cloudcover, strSunrise, strSunset, morningTemp, afternoonTemp,
                    eveningTemp, nightTemp, dateTimeF);

            mainActivity.runOnUiThread(() -> mainActivity.updateWeatherDetails(weather, s, arrObjDataHourly));
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }
}
