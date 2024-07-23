package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.http.HTTPConnection;
import com.acn.jive.mastadonweatherbot.mastodon.PostStatus;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiService;

import java.util.Optional;

public class Main {
    public void run() {
        HTTPConnection HTTPConnection = new HTTPConnection();
        WeatherApiService weatherApiService = new WeatherApiService();
        PostStatus postStatus = new PostStatus();
        try {
            Optional<Weather> weather = weatherApiService.readWeatherData("Berlin", HTTPConnection);
            postStatus.execute(weather.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }


}
