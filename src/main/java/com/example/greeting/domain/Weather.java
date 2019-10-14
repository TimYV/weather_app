package com.example.greeting.domain;

import javax.persistence.*;

@Entity
@Table(name="weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "City_id", nullable = false)
    private Long cityId;
    @Column(name = "Wind", nullable = false)
    private Double wind;
    @Column(name = "Humidity", nullable = false)
    private Double humidity;
    @Column(name = "Temperature", nullable = false)
    private Double temperature;

    public Weather() {
    }

    public Weather(Long cityId, Double wind, Double humidity, Double temperature) {
        this.cityId = cityId;
        this.wind = wind;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
            this.wind = wind;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", city='" + cityId + '\'' +
                ", wind=" + wind +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                '}';
    }
}
