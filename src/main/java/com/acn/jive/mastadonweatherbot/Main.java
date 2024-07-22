package com.acn.jive.mastadonweatherbot;

public class Main {
    public void run() {
        Connection connection = new Connection();
        WeatherApiService weatherApiService = new WeatherApiService();
        try{
            weatherApiService.readWeatherData("Berlin", connection);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }


}