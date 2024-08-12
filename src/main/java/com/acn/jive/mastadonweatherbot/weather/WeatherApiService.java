package com.acn.jive.mastadonweatherbot.weather;

import com.acn.jive.mastadonweatherbot.http.HttpConnection;
import com.acn.jive.mastadonweatherbot.http.HttpException;
import com.acn.jive.mastadonweatherbot.persistence.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApiService {

    public WeatherApiResponse readWeatherDataByCoordinates (Location location, HttpConnection HTTPConnection) throws WeatherException {
        try {
            WeatherApiResponse weatherApiResponse = new WeatherApiResponse();

            String API_KEY = System.getenv("weather_api");
            BigDecimal latitude = location.getLatitude();
            BigDecimal longitude = location.getLongitude();
            String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + latitude + "," + longitude;

            weatherApiResponse.setRequestTimestamp(LocalDateTime.now());
            HttpURLConnection apiConnection = HTTPConnection.createApiConnection(url);
            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return weatherApiResponse;
            }

            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            weatherApiResponse.setJsonObject(jsonObject);
            return weatherApiResponse;
        } catch (IOException | ParseException | HttpException ex){
            throw new WeatherException("An exception occurred while reading the weather data", ex);
        }
    }

    public Weather createWeatherObjectFromJson(JSONObject jsonObject) {
        JSONObject locationWeatherJson = (JSONObject) jsonObject.get("location");
        JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
        JSONObject condition = (JSONObject) currentWeatherJson.get("condition");

        String city = (String) locationWeatherJson.get("name");
        double temperature = (double) currentWeatherJson.get("temp_c");
        String description = (String) condition.get("text");
        String dateTime = (String) locationWeatherJson.get("localtime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        String iconUrl = (String) condition.get("icon");
        System.out.println("URL: " + iconUrl);

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setTemperature(temperature);
        weather.setDescription(description);
        weather.setLocalTime(localDateTime);
        weather.setIconUrl(iconUrl);
        return weather;
    }

    private String readApiResponse(HttpURLConnection apiConnection) throws WeatherException {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();
        } catch (IOException ex) {
            throw new WeatherException("An exception occurred while reading the Weather API response", ex);
        }
    }
}
