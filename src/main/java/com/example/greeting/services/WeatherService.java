package com.example.greeting.services;

import com.example.greeting.domain.City;
import com.example.greeting.domain.Weather;
import com.example.greeting.repos.CityRepo;
import com.example.greeting.repos.WeatherRepo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    WeatherRepo weatherRepo;

    @Autowired
    CityRepo cityRepo;

    final private String source = "http://api.openweathermap.org/data/2.5/weather?q=";
    final private String API_KEY = "someAPI"; // убрал API (нужно заменить на свой)

    @CachePut(value = "weather")
    public void refreshWeather() throws InterruptedException, IOException {
        for (City value : cityRepo.findAll()) {
            String city = value.getName();
            StringBuilder result = getWeatherInfoByCiteName(value.getName());
            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            List<City> cityItem = cityRepo.findByName(city);
            Weather weather = new Weather(
                    cityItem.get(0).getId(),
                    (Double) windMap.get("speed"),
                    (Double) mainMap.get("humidity"),
                    (Double) mainMap.get("temp")
            );
            weatherRepo.save(weather);
            logger.info("New data about forecast was successfully saved in database: " + weather);
        }
        Thread.sleep(1000L);
    }

    private StringBuilder getWeatherInfoByCiteName(String city) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(source + city +
                "&appid=" + API_KEY + "&units=imperial");
        URLConnection connection = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        logger.info("New data about the weather was got from " + source);
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result;
    }


    private Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }

}
