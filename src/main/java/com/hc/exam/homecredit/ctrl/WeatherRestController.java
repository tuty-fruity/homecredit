package com.hc.exam.homecredit.ctrl;

import com.hc.exam.homecredit.dto.WeatherDTO;
import com.hc.exam.homecredit.model.Weather;
import com.hc.exam.homecredit.repository.WeatherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class WeatherRestController {

    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;
    private final URI appURI;
    private final String key;

    @Autowired
    public WeatherRestController(RestTemplate restTemplate, WeatherRepository weatherRepository, URI appURI, String key) {
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
        this.appURI = appURI;
        this.key = key;
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<Collection<WeatherDTO>> getWeatherForThreeCities() {

        Manager m = new Manager();
        Collection<WeatherDTO> weatherList = Stream.of("London", "Prague", "San Francisco")
                .map(city -> {
                    String endPoint = String.format(appURI.toString().concat("?q=%s&APPID=%s"), city, key);
                    WeatherDTO weather = restTemplate.getForObject(endPoint, WeatherDTO.class);
                    return weather;
                })
                .collect(Collectors.toList());


        weatherList.stream().forEach(weather -> {
            m.collect(weather);
        });

        weatherRepository.findAll().forEach(System.out::println);
        return ResponseEntity.ok(weatherList);
    }

    @RequestMapping(value = "/saveWeather", method = RequestMethod.POST)
    public ResponseEntity<?> storeWeather() throws URISyntaxException {

        return ResponseEntity.ok().build();
    }

    private class Manager {

        List<Weather> weatherList = new ArrayList<>();

        Manager() {
        }

        void collect(WeatherDTO newWeather) {
            Weather weather = new Weather();
            weather.setResponseId(newWeather.getId());
            weather.setActualWeather(newWeather.getWeather());
            weather.setLocation(newWeather.getLocation());
            weather.setTemperature(newWeather.getTemperature());
            weather.setResponseId(newWeather.getId());

            weatherRepository.save(weather);
        }
    }
}


