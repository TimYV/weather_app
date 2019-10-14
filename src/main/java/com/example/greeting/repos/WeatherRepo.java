package com.example.greeting.repos;

import com.example.greeting.domain.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepo extends CrudRepository<Weather, Long> {

    List<Weather> findTop1ByCityId(Long city);

}
