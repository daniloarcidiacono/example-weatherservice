package com.objectway.stage.examples.weatherservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "forecast.service.random")
public class RandomForecastServiceProperties {
    /**
     * Minimum humidity (percentage)
     */
    private int minHumidity = 0;

    /**
     * Maximum humidity (percentage)
     */
    private int maxHumidity = 100;

    /**
     * Minimum rainfall (percentage)
     */
    private int minRainfall = 0;

    /**
     * Maximum rainfall (percentage)
     */
    private int maxRainfall = 100;

    /**
     * Minimum temperature (Celsius)
     */
    private int minTemperature = -20;

    /**
     * Maximum temperature (celsius)
     */
    private int maxTemperature = 45;

    /**
     * Minimum wind (kmh)
     */
    private int minWind = 0;

    /**
     * Maximum wind (kmh)
     */
    private int maxWind = 10;

    public int getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(int minHumidity) {
        this.minHumidity = minHumidity;
    }

    public int getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(int maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public int getMinRainfall() {
        return minRainfall;
    }

    public void setMinRainfall(int minRainfall) {
        this.minRainfall = minRainfall;
    }

    public int getMaxRainfall() {
        return maxRainfall;
    }

    public void setMaxRainfall(int maxRainfall) {
        this.maxRainfall = maxRainfall;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMinWind() {
        return minWind;
    }

    public void setMinWind(int minWind) {
        this.minWind = minWind;
    }

    public int getMaxWind() {
        return maxWind;
    }

    public void setMaxWind(int maxWind) {
        this.maxWind = maxWind;
    }
}
