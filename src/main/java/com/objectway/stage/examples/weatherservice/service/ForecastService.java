package com.objectway.stage.examples.weatherservice.service;

import com.objectway.stage.examples.weatherservice.model.WeatherDaySample;
import com.objectway.stage.examples.weatherservice.model.domain.TemperatureUnit;
import com.objectway.stage.examples.weatherservice.model.domain.WeekDay;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Service for weather forecasts.
 */
public interface ForecastService {
    /**
     * Returns the weather forecast for the specified location and weekday.
     * @param location the location (cannot be null)
     * @param weekDay the day of the week (cannot be null)
     * @param temperatureUnit the unit to use for temperatures (can be null, default {@link TemperatureUnit#CELSIUS})
     * @return the requested weather forecast
     * @throws IllegalArgumentException if location or weekday is null
     */
    @NonNull WeatherDaySample getForecastsForDay(@NonNull final String location,
                                                 @NonNull final WeekDay weekDay,
                                                 @Nullable final TemperatureUnit temperatureUnit);
}
