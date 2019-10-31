package com.objectway.stage.examples.weatherservice.service.random;

import com.objectway.stage.examples.weatherservice.model.WeatherDaySample;
import com.objectway.stage.examples.weatherservice.model.WeatherHourSample;
import com.objectway.stage.examples.weatherservice.model.domain.Forecast;
import com.objectway.stage.examples.weatherservice.model.domain.TemperatureUnit;
import com.objectway.stage.examples.weatherservice.model.domain.WeekDay;
import com.objectway.stage.examples.weatherservice.properties.RandomForecastServiceProperties;
import com.objectway.stage.examples.weatherservice.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ForecastService} that returns random data :)
 */
@Service
public class RandomForecastService implements ForecastService {
    private static final Random random = new Random();

    @Autowired
    private RandomForecastServiceProperties properties;

    @Override
    public WeatherDaySample getForecastsForDay(final String location,
                                               final WeekDay weekDay,
                                               final TemperatureUnit temperatureUnit) {
        // Sanity checks
        if (location == null) {
            throw new IllegalArgumentException("location parameter cannot be null");
        }

        if (weekDay == null) {
            throw new IllegalArgumentException("weekDay parameter cannot be null");
        }

        final WeatherDaySample daySample = new WeatherDaySample();
        final TimeSeriesGenerator humidityGenerator = new TimeSeriesGenerator(24, properties.getMinHumidity(), properties.getMaxHumidity());
        final TimeSeriesGenerator rainfallGenerator = new TimeSeriesGenerator(24,  properties.getMinRainfall(), properties.getMaxRainfall());
        final TimeSeriesGenerator temperatureGenerator = new TimeSeriesGenerator(24, properties.getMinTemperature(), properties.getMaxTemperature());
        final TimeSeriesGenerator windGenerator = new TimeSeriesGenerator(24, properties.getMinWind(), properties.getMaxWind());

        for (int i = 0; i < 24; i++) {
            final WeatherHourSample hourSample = new WeatherHourSample();
            hourSample.setForecast(randomForecast());
            hourSample.setHumidity(humidityGenerator.next());
            hourSample.setRainfall(rainfallGenerator.next());
            hourSample.setTemperature(toUnit(temperatureGenerator.next(), temperatureUnit));
            hourSample.setWind(windGenerator.next());

            daySample.getSamples()[i] = hourSample;
        }

        // Compute the minimum temperature of the day
        daySample.setMinTemperature(
            toUnit(
                Arrays.stream(daySample.getSamples())
                    .map(WeatherHourSample::getTemperature)
                    .min(Comparator.comparing(Function.identity()))
                    .orElseThrow(IllegalArgumentException::new),
                temperatureUnit
            )
        );

        // Compute the maximum temperature of the day
        daySample.setMaxTemperature(
            toUnit(
                Arrays.stream(daySample.getSamples())
                    .map(WeatherHourSample::getTemperature)
                    .max(Comparator.comparing(Function.identity()))
                    .orElseThrow(IllegalArgumentException::new),
                temperatureUnit
            )
        );

        // Compute the most frequent (mode) forecast type
        daySample.setForecast(
            Arrays.stream(daySample.getSamples())
                .collect(
                    Collectors.groupingBy(
                        WeatherHourSample::getForecast,
                        TreeMap::new,
                        Collectors.counting()
                    )
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalArgumentException::new).toString()
        );

        return daySample;
    }

    /**
     * Returns a random forecast
     * @return a random forecast
     */
    private static Forecast randomForecast() {
        final int max = Forecast.values().length;
        return Forecast.values()[random.nextInt(max)];
    }

    /**
     * Given a temperature in celsius, converts it to the target unit.
     * @param celsius the temperature in celsius
     * @param target the target temperature unit
     * @return the temperature in the desired target unit
     */
    private static int toUnit(final int celsius, final TemperatureUnit target) {
        if (TemperatureUnit.CELSIUS.equals(target)) {
            return celsius;
        }

        return Math.round((celsius * 9 / 5f) + 32);
    }
}
