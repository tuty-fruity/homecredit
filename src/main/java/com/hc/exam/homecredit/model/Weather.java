package com.hc.exam.homecredit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "WeatherLog")
public class Weather {

    @Id
    @GeneratedValue
    private Long id;

    private String responseId;
    private String location;
    private String actualWeather;
    private String temperature;
    private Timestamp dtimeinserted;

    public Weather() { }

    public Weather(String responseId, String location, String actualWeather, String temperature, Timestamp dtimeinserted) {
        this.responseId = responseId;
        this.location = location;
        this.actualWeather = actualWeather;
        this.temperature = temperature;
        this.dtimeinserted = dtimeinserted;
    }

    public Long getId() {
        return id;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getLocation() {
        return location;
    }

    public String getActualWeather() {
        return actualWeather;
    }

    public String getTemperature() {
        return temperature;
    }

    public Timestamp getDtimeinserted() {
        return dtimeinserted;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActualWeather(String actualWeather) {
        this.actualWeather = actualWeather;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDtimeinserted(Timestamp dtimeinserted) {
        this.dtimeinserted = dtimeinserted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(responseId, weather.responseId) &&
                Objects.equals(location, weather.location) &&
                Objects.equals(actualWeather, weather.actualWeather) &&
                Objects.equals(temperature, weather.temperature) &&
                Objects.equals(dtimeinserted, weather.dtimeinserted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(responseId, location, actualWeather, temperature, dtimeinserted);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", responseId='" + responseId + '\'' +
                ", location='" + location + '\'' +
                ", actualWeather='" + actualWeather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dtimeinserted=" + dtimeinserted +
                '}';
    }
}
