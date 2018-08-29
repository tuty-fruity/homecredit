package com.hc.exam.homecredit.ctrl;

import com.hc.exam.homecredit.dto.WeatherDTO;
import com.hc.exam.homecredit.helperbean.WeatherResponseCollector;
import com.hc.exam.homecredit.model.Weather;
import com.hc.exam.homecredit.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/weather")
public class WeatherRestController {

    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;
    private final URI apiURI;

    private final WeatherResponseCollector weatherResponseCollector;

    @Autowired
    public WeatherRestController(RestTemplate restTemplate,
                                 WeatherRepository weatherRepository,
                                 WeatherResponseCollector weatherResponseCollector,
                                 URI apiURI) {
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
        this.weatherResponseCollector = weatherResponseCollector;
        this.apiURI = apiURI;
    }

    @RequestMapping(value = "/listInfo", method = RequestMethod.GET)
    public ResponseEntity<Collection<WeatherDTO>> getWeatherForThreeCities() {

        Collection<WeatherDTO> weatherList = Stream.of("London", "Prague", "San Francisco")
                .map(city -> {
                    String endPoint = apiURI.toString().concat("&q=" + city);
                    WeatherDTO weather = restTemplate.getForObject(endPoint, WeatherDTO.class);
                    return weather;
                })
                .collect(Collectors.toList());

        weatherList.stream().forEach(weather -> {
            weatherResponseCollector.collect(weather);
        });

        return ResponseEntity.ok(weatherList);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public ResponseEntity<?> storeWeather() {
        Set<Weather> weatherSet = weatherResponseCollector.getWeatherSet();

        if (weatherSet.size() != 0) {
            int skipSize = weatherSet.size() - 5; // to get only the last 5

            if (skipSize > -1) { // skipSize cannot be negative

                Supplier<Stream<Weather>> latestWeatherSupplier = () -> weatherSet.stream().skip(skipSize)
                        .filter(weather -> (weather.getDtimeinserted() == null));

                /*
                *   save the last 5 unique weathers to db
                * */
                if (latestWeatherSupplier.get().count() == 5) {
                    latestWeatherSupplier.get().forEach(weather -> {
                        weather.setDtimeinserted(new Timestamp(System.currentTimeMillis()));
                        weatherRepository.save(weather);
                    });

                    weatherRepository.findAll().forEach(System.out::println);
                    return ResponseEntity.ok().build();
                }
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Collection<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }
}


