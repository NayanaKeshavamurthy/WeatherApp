# Visual Crossing Weather Mobile App

## Overview

This Android app provides users with detailed weather information for a specified location, including current weather, hourly forecast (48 hours), and daily forecast (15 days). The app utilizes Visual Crossing Weather Data & API for weather data.

## Features

- Two activities: Home Weather Screen and Daily Forecast Screen.
- Alternate layout for landscape orientation on the home weather screen.
- Toggle between imperial and metric units (C/F) via options-menu icon.
- Daily forecast accessible through the calendar icon in the options menu.
- Change location through the location icon in the options menu.
- 7-day weather forecast with portrait and landscape views.
- Time conversion, handling no-network situations, and wind direction conversion.
- Swipe refresh capability for reloading weather data.
- Save user settings to remember the selected location and units upon app restart.

## API Key

To access weather data, you need to set up a free account and obtain an API key from Visual Crossing Weather Data & API. Create your account at [Visual Crossing Weather](https://www.visualcrossing.com/sign-up). Retrieve your API key from the "Your Details" section under the "Key" label. The free account has a limit of 1000 calls/day.

## Application/Behavior Diagrams

### 1. App Main Screen

- Daily Weather RecyclerView with current conditions, hourly forecast, and 48-hourly weather entries.
- Location name, current date/time, temperature, feels like, humidity, UV index, sunrise, sunset, and more.
- Options menu with toggle units, show daily forecast, and change location.
- Weather icons, wind details, visibility, and daily entries.

### 2. 7-Day Weather Portrait & Landscape

- Location, day & date, high/low temperature, description, precipitation probability, UV index, weather icon, and temperature details.

### 3. Options Menu

- Change Units: Toggle between imperial and metric units.
- Daily Forecast: Open the daily forecast activity.
- Change Location: Display AlertDialog for entering a new location.

### 4. Weather Icons

- Weather icons in JSON data mapped to image files in the drawable resource folder.

### 5. Handling No-Network Situations

- Display "No internet connection" when there's no network.
- Options-menu selections disabled in no-network situations.

### 6. Time Conversions

- Convert Epoch data fields to actual Date objects and format as needed.

### 7. Converting Wind Direction

- Display wind direction using compass points (N, SE, W, etc.).

### 8. Other Features

- Swipe Refresh: Reload weather data for the current location.
- Saving User Settings: Save selected location and units for app restart.

## Usage

1. Clone the repository.
2. Open the project in Android Studio.
3. Obtain an API key from Visual Crossing Weather Data & API and replace `YOUR_API_KEY` in the code with your key.
4. Build and run the app on an Android emulator or device.

## Permissions

Ensure the `ACCESS_NETWORK_STATE` and `INTERNET` permissions are declared in the project manifest.

