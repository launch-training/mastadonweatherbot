package com.acn.jive.mastadonweatherbot.weather;

import com.acn.jive.mastadonweatherbot.http.HTTPConnection;
import com.acn.jive.mastadonweatherbot.persistence.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class WeatherApiService {

    public Optional<JSONObject> readWeatherDataByCoordinates (Location location, HTTPConnection HTTPConnection) throws WeatherException {
        try {
            // 1. Fetch the API response based on API Link
            String API_KEY = "437ff40d4bf2412581d135526241807";
            BigDecimal latitude = location.getLatitude();
            BigDecimal longitude = location.getLongitude();
            String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + latitude + "," + longitude; // ab ? request param
            HttpURLConnection apiConnection = HTTPConnection.createApiConnection(url);

            // check for response status
            // 200 - means that the connection was a success
            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return Optional.empty();
            }

            // 2. Read the response and convert store String type
            String jsonResponse = readApiResponse(apiConnection);

            // 3. Parse the string into a JSON Object
            JSONParser parser = new JSONParser();
            return Optional.of((JSONObject) parser.parse(jsonResponse));

        } catch(IOException | ParseException e){
            throw new WeatherException(e.getMessage(), e);
        }
    }

    public Weather createWeatherObjectFromJson(JSONObject jsonObject) {
        JSONObject locationWeatherJson = (JSONObject) jsonObject.get("location");
        JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
        JSONObject condition = (JSONObject) currentWeatherJson.get("condition");

        // Store the data into their corresponding data type
        String city = (String) locationWeatherJson.get("name");
        double temperature = (double) currentWeatherJson.get("temp_c");
        String description = (String) condition.get("text");

        String dateTime = (String) locationWeatherJson.get("localtime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        String iconUrl = (String) condition.get("icon");
        System.out.println("URL: " + iconUrl);

        // create object
        Weather weather = new Weather();
        weather.setCity(city);
        weather.setTemperature(temperature);
        weather.setDescription(description);
        weather.setLocalTime(localDateTime);
        weather.setIconUrl(iconUrl);
        return weather;
    }

    public Optional<Weather> readWeatherDataByCity(String city, HTTPConnection HTTPConnection) throws WeatherException {
        try{
            // 1. Fetch the API response based on API Link
            String API_KEY = "437ff40d4bf2412581d135526241807";
            String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city; // ab ? request param
            HttpURLConnection apiConnection = HTTPConnection.createApiConnection(url);

            // check for response status
            // 200 - means that the connection was a success
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return Optional.empty();
            }

            // 2. Read the response and convert store String type
            String jsonResponse = readApiResponse(apiConnection);

            // 3. Parse the string into a JSON Object
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject locationWeatherJson = (JSONObject) jsonObject.get("location");
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
            JSONObject condition = (JSONObject) currentWeatherJson.get("condition");
//            System.out.println(currentWeatherJson.toJSONString());

            // 4. Store the data into their corresponding data type
            double temperature = (double) currentWeatherJson.get("temp_c");

            String description = (String) condition.get("text");

            String dateTime = (String) locationWeatherJson.get("localtime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

            String iconUrl = (String) condition.get("icon");
            System.out.println("URL: " + iconUrl);

            // create object
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setTemperature(temperature);
            weather.setDescription(description);
            weather.setLocalTime(localDateTime);
            weather.setIconUrl(iconUrl);
            return Optional.of(weather);

        }catch(IOException | ParseException e){
             throw new WeatherException(e.getMessage(), e);
        }
    }

    private String readApiResponse(HttpURLConnection apiConnection) {
        try {
            // Create a StringBuilder to store the resulting JSON data
            StringBuilder resultJson = new StringBuilder();

            // Create a Scanner to read from the InputStream of the HttpURLConnection
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            // Loop through each line in the response and append it to the StringBuilder
            while (scanner.hasNext()) {
                // Read and append the current line to the StringBuilder
                resultJson.append(scanner.nextLine());
            }

            // Close the Scanner to release resources associated with it
            scanner.close();

            // Return the JSON data as a String
            return resultJson.toString();

        } catch (IOException e) {
            // Print the exception details in case of an IOException
            e.printStackTrace();
        }

        // Return null if there was an issue reading the response
        return null;
    }
}
