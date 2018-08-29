package com.hc.exam.homecredit.helperbean;

import com.hc.exam.homecredit.dto.WeatherDTO;
import com.hc.exam.homecredit.model.Weather;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class WeatherResponseCollector {
    // to store unique weather, see Weather::equals()
    Set<Weather> weatherSet = new LinkedHashSet<>();

    public WeatherResponseCollector() {
    }

    public void collect(WeatherDTO newWeather) {
        Weather weather = new Weather();

        weather.setResponseId(newWeather.getId());
        weather.setActualWeather(newWeather.getWeather());
        weather.setLocation(newWeather.getLocation());
        weather.setTemperature(newWeather.getTemperature());

        weatherSet.add(weather);
    }

    public Set<Weather> getWeatherSet() {
        return weatherSet;
    }
}
