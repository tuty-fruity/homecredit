package com.hc.exam.homecredit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

public class WeatherDTO {


    private String id;
    private String weather;
    private String temperature;

    @JsonProperty("name")
    private String location;

    @JsonProperty("weather")
    private void unpackWeather(Map<String, String>[] weather) {
        this.weather = weather[0].get("main");
    }

    @JsonProperty("main")
    private void unpackTemperature(Map<String, String> temperature) {
        this.temperature = temperature.get("temp");
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }
}

