package org.example.weatherapp;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private static final String API_KEY = "e2dc02a86d071f95ef06b2c057be1b4d";

    public static WeatherInfo getWeather(String city) {
        WeatherInfo info = new WeatherInfo();
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(conn.getInputStream()))
                        .getAsJsonObject();
                info.city = jsonObject.get("name").getAsString();
                info.temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                info.description = jsonObject.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();
                info.condition = jsonObject.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("main").getAsString();
            } else {
                info.error = "City not found!";
            }
        } catch (Exception e) {
            info.error = "Error: " + e.getMessage();
        }
        return info;
    }

    public static class WeatherInfo {
        public String city;
        public double temp;
        public String description;
        public String condition;
        public String error;
    }
}

