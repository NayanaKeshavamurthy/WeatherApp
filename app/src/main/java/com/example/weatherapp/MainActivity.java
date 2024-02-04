package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView currentDateTime, temperature, feelsLike, weatherDesc, winds, humidity, uvIndex, visibility, morningTemp, afternoonTemp, eveningTemp, nightTemp, sunrise, sunset, morningText, afternoonText, eveningText, nightText;

    ImageView weatherImage;

    //HourWeatherAdapter mAdapter;
    RecyclerView hourlyWeatherRecycler;

    SwipeRefreshLayout refresher;

    private SharedPreferences sharedPref;

    String defaultLocation;

    MenuItem tempUnits, dailyForecast, location;

    boolean temperatureScaleF = true;
    String jsonWeatherData;
    Weather weatherData;
    ArrayList<HourlyWeather> hourlyWeatherData;

    ConstraintLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hourlyWeatherRecycler = findViewById(R.id.hourlyWeatherRecycler);

        currentDateTime = findViewById(R.id.currentDateTime);
        temperature = findViewById(R.id.temperature);
        feelsLike = findViewById(R.id.feelsLike);
        weatherDesc = findViewById(R.id.weatherDesc);
        winds = findViewById(R.id.winds);
        humidity = findViewById(R.id.humidity);
        uvIndex = findViewById(R.id.uvIndex);
        visibility = findViewById(R.id.visibility);
        morningTemp = findViewById(R.id.morningTemp);
        afternoonTemp = findViewById(R.id.afternoonTemp);
        eveningTemp = findViewById(R.id.eveningTemp);
        nightTemp = findViewById(R.id.nightTemp);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        morningText = findViewById(R.id.morningText);
        afternoonText = findViewById(R.id.afternoonText);
        eveningText = findViewById(R.id.eveningText);
        nightText = findViewById(R.id.nightText);

        mainlayout = (ConstraintLayout) this.findViewById(R.id.mainLayout);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#61553f"));
        actionBar.setBackgroundDrawable(colorDrawable);

        // initialize the refresher
        refresher = findViewById(R.id.refresher);
        weatherImage  = findViewById(R.id.weatherImage);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // shared preferences for saving the data
        defaultLocation = sharedPref.getString("location","Chicago,IL");
        temperatureScaleF = sharedPref.getBoolean("scaleSelection",true);

        refresher.setOnRefreshListener(() ->
        {
            // check the network connection
            if (hasNetworkConnection())
            {
                // call a volley request
                setTitle(sharedPref.getString("location",""));
                WeatherDownloader.downloadWeather(MainActivity.this, sharedPref.getString("location",""), temperatureScaleF, sharedPref);
                for (int i = 0; i < mainlayout.getChildCount(); i++)
                {
                    View child = mainlayout.getChildAt(i);
                    if (child.getId() != R.id.currentDateTime)
                    {
                        child.setVisibility(View.VISIBLE);
                    }
                }
                refresher.setRefreshing(false);
            }
            else
            {
                // if network is not available, display a toast message
                Toast.makeText(this,"There is no network connection on the device",Toast.LENGTH_LONG).show();
                refresher.setRefreshing(false);
            }
        });


        if (!hasNetworkConnection() )
        {
            if(savedInstanceState == null || savedInstanceState.getString("location") == null) {
                setTitle(getString(R.string.app_name));
                TextView textView = (TextView) findViewById(R.id.currentDateTime);
                textView.setText("No internet connection");
                for (int i = 0; i < mainlayout.getChildCount(); i++) {
                    View child = mainlayout.getChildAt(i);
                    if (child.getId() != R.id.currentDateTime) {
                        child.setVisibility(View.GONE);
                    }
                }
            }
            else
            {
                defaultLocation = savedInstanceState.getString("location");
                temperatureScaleF = savedInstanceState.getBoolean("tempUnits");
                weatherData = (Weather) savedInstanceState.getSerializable("weatherData");
                jsonWeatherData = savedInstanceState.getString("jsonData");
                hourlyWeatherData = (ArrayList<HourlyWeather>) savedInstanceState.getSerializable("hourlyData");
                setTitle(defaultLocation);
                updateWeatherDetails(weatherData, jsonWeatherData,hourlyWeatherData);

            }
        }


        if (hasNetworkConnection())
        {
            // set title as a Location and start the parsing and processing of information for app
            setTitle(defaultLocation);
            WeatherDownloader.downloadWeather(MainActivity.this, defaultLocation, temperatureScaleF, sharedPref);
        }

    }

    public void callToast(){
        Toast.makeText(this,"Please enter a correct location", Toast.LENGTH_LONG).show();
    }
    private boolean hasNetworkConnection()
    {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    private String getDirection(double degrees)
    {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        // We'll use 'X' as the default if we get a bad value
        return "X";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // instantiate the menu
        getMenuInflater().inflate(R.menu.menu, menu);

        tempUnits = menu.findItem(R.id.menuTempUnits);
        dailyForecast = menu.findItem(R.id.menuDailyForecast);
        location = menu.findItem(R.id.menuLocation);

        // set the scale used as per the selection before
        if (temperatureScaleF)
            tempUnits.setIcon(R.drawable.units_f);
        else
            tempUnits.setIcon(R.drawable.units_c);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // tempeUnits menu selected
        if (item.getItemId() == R.id.menuTempUnits)
        {
            if (!hasNetworkConnection())
            {
                Toast.makeText(this,"The function cannot be used when there is no network connection",Toast.LENGTH_LONG).show();
            }
            else
            {
                defaultLocation = sharedPref.getString("location","");
                // Change the scale and update all the values accordingly
                if (!temperatureScaleF)
                {
                    temperatureScaleF = true;
                    sharedPref.edit().putBoolean("scaleSelection", temperatureScaleF).apply();
                    item.setIcon( R.drawable.units_f);
                    WeatherDownloader.downloadWeather(MainActivity.this,  defaultLocation, temperatureScaleF,sharedPref);

                }
                else
                {
                    temperatureScaleF = false;
                    sharedPref.edit().putBoolean("scaleSelection", temperatureScaleF).apply();
                    item.setIcon(R.drawable.units_c);
                    WeatherDownloader.downloadWeather(MainActivity.this,  defaultLocation, temperatureScaleF,sharedPref);

                }
            }
            return true;
        }
        // daily forecast menu selected
        else if (item.getItemId() == R.id.menuDailyForecast)
        {
            if (!hasNetworkConnection())
            {
                Toast.makeText(this,"The function cannot be used when there is no network connection",Toast.LENGTH_LONG).show();
            }
            else
            {
                // display the days view of 15 days using the daily forecast adapter
                Intent intent = new Intent(this, DailyForecast.class);
                intent.putExtra("dailyData", jsonWeatherData);
                intent.putExtra("scaleSelection", temperatureScaleF);
                intent.putExtra("location", defaultLocation);

                //launch the activity
                startActivity(intent);
            }
            return true;
        }
        // location menu selected
        else if (item.getItemId() == R.id.menuLocation)
        {
            if (!hasNetworkConnection())
            {
                Toast.makeText(this,"The function cannot be used when there is no network connection",Toast.LENGTH_LONG).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // display dialog to enter location from the user
                final EditText locationInput = new EditText(this);
                locationInput.setInputType(InputType.TYPE_CLASS_TEXT);
                locationInput.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(locationInput);

                builder.setTitle("Enter a Location");
                builder.setMessage("For US locations, enter as 'City',or 'City,State'" + "\n \n" + "For international locations enter as 'City,Country'");

                // accepts a response and check if the appropriate location is given by user
                builder.setPositiveButton("OK", (dialog, id) ->
                {
                   // defaultLocation = getLocationName(locationInput.getText().toString().trim());

                    // relevant location
                    if(defaultLocation != null)
                    {
                        // location taken from the user
                        WeatherDownloader.downloadWeather(MainActivity.this,  locationInput.getText().toString().trim(), temperatureScaleF,sharedPref);
                    }

                });

                builder.setNegativeButton("CANCEL", (dialog, id) -> dialog.dismiss());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

    }

    public void updateWeatherDetails(Weather weather, String jsonData, ArrayList<HourlyWeather> hourlyWeather){
        try {
            if (weather != null) {
                setTitle(sharedPref.getString("location",""));

                // Update the json data to the controls
                jsonWeatherData = jsonData;
                weatherData = weather;
                hourlyWeatherData = hourlyWeather;
                String formatted = "";
                String scaleUsed = (temperatureScaleF ? "F" : "C");
                String windScale = (temperatureScaleF ? "mph" : "kph");
                String visibilityScale = (temperatureScaleF ? "mi" : "km");

                // format each data and set it to appropriate controls
                formatted = String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getMorningTemperature()));
                morningTemp.setText(formatted);

                formatted = String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getAfternoonTemperature()));
                afternoonTemp.setText(formatted);

                formatted = String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getEveningTemperature()));
                eveningTemp.setText(formatted);

                formatted = String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getNightTemperature()));
                nightTemp.setText(formatted);

                // set attributes
                formatted = String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getTemperature()));
                temperature.setText(formatted);

                formatted = "Winds: " + getDirection(Double.parseDouble(weather.getWindDirection())) +
                       " at " + Double.parseDouble(weather.getWindSpeed()) +" " + windScale + " ";

                if(!weather.getWindGust().equals("null"))
                    formatted += "gusting to " + Double.parseDouble(weather.getWindGust()) + " " + windScale + " ";
                winds.setText(formatted);

                formatted = String.format("%s (%s)", weather.getConditions(), weather.getCloudCover());
                weatherDesc.setText(formatted);

                sunrise.setText("Sunrise : " + weather.getSunrise());
                sunset.setText("Sunset : " + weather.getSunset());

                formatted = String.format(Locale.getDefault(), "Humidity: %.0f%%",
                        Double.parseDouble(weather.getHumidity()));
                humidity.setText(formatted);

                // set the image to the image resource
                int iIcon = getResources().getIdentifier(weather.icon,  "drawable", this.getPackageName());
                weatherImage.setImageResource(iIcon);

                // set more weather attributes
                formatted = String.format("%s", weather.getUvIndex());
                uvIndex.setText("UV Index : "+ formatted);

                formatted = "Feels Like " + String.format("%.0f° " + scaleUsed, Double.parseDouble(weather.getFeelsLike()));
                feelsLike.setText(formatted);

                formatted = weather.getVisibility().length() > 7? weather.getVisibility().substring(0, 7):weather.getVisibility();
                formatted = "Visibility : " + String.format("%s",   formatted + " " + visibilityScale + " ");
                visibility.setText(formatted);

                currentDateTime.setText(weather.getTimeZone());

                hourlyWeatherRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

                //  pass the hourly data array to the hourly handler adapter to display the recycle view
                HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeather, MainActivity.this);
                hourlyWeatherRecycler.setAdapter(hourlyWeatherAdapter);
            }
            else
            {
                Toast.makeText(this, "Weather Object Null. Please check details", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // saving the instance before there is change in orientation
        outState.putString("location", defaultLocation);
        outState.putBoolean("tempUnits", temperatureScaleF);
        outState.putString("jsonData", jsonWeatherData);
        outState.putSerializable("weatherData", weatherData);
        outState.putSerializable("hourlyData", hourlyWeatherData);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // restoring the state as previous before there is change in orientation
        super.onRestoreInstanceState(savedInstanceState);

    }

}