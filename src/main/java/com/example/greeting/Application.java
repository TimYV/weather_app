package com.example.greeting;

import com.example.greeting.domain.City;
import com.example.greeting.repos.CityRepo;
import com.example.greeting.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class Application {

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(initialDelay=1000L, fixedDelayString = "${someJob.delay}")
    void someJob() throws InterruptedException, IOException {
        weatherService.refreshWeather();

    }

    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name="scheduling.enabled", matchIfMissing=true)
    class SchedulingConfiguration {}

    @Bean
    CommandLineRunner runner(CityRepo cityRepo) {
        return args -> {
            cityRepo.save(new City("London"));
            cityRepo.save(new City("Berlin"));
            cityRepo.save(new City("Paris"));
        };
    }

}
