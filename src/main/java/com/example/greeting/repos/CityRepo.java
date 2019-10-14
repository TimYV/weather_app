package com.example.greeting.repos;

import com.example.greeting.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {

        List<City> findByName(String city);

}
