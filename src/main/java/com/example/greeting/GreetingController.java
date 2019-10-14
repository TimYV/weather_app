package com.example.greeting;

import com.example.greeting.domain.City;
import com.example.greeting.domain.Weather;
import com.example.greeting.repos.CityRepo;
import com.example.greeting.repos.WeatherRepo;
import com.example.greeting.services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherRepo weatherRepo;
    @Autowired
    private CityRepo cityRepo;

    @GetMapping("/greeting")
    public String greeting(Map<String, Object> model) {
        model.put("city", "No one chosen");
        return "greeting";
    }

    @PostMapping("find")
    @Cacheable(value = "weather")
    public String find(@RequestParam String city, Map<String, Object> model) {
        List<City> cityItem = cityRepo.findByName(city);
        if (cityItem == null || cityItem.isEmpty()) {
            model.put("city", "No such city in the data base");
            logger.info("No such city in the data base");
            return "greeting";
        }
        model.put("city", city);
        Iterable<Weather> weathers = weatherRepo.findTop1ByCityId(cityItem.get(0).getId());
        model.put("weathers", weathers);
        return "greeting";
    }

}