package com.objectway.stage.examples.weatherservice.controller;

import com.objectway.stage.examples.weatherservice.dto.WeatherWeekDTO;
import com.objectway.stage.examples.weatherservice.model.WeatherDaySample;
import com.objectway.stage.examples.weatherservice.model.domain.TemperatureUnit;
import com.objectway.stage.examples.weatherservice.model.domain.WeekDay;
import com.objectway.stage.examples.weatherservice.service.ForecastService;
import com.objectway.stage.examples.weatherservice.validation.Domain;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Api(value = "Weather Forecast System", produces = "application/json")
public class WeatherController {
    @Autowired
    private ForecastService forecastService;

    @GetMapping("/forecast/{location}/week")
    @ApiOperation(value = "Gets the weekly forecast")
    public WeatherWeekDTO getWeeklyForecast(@PathVariable
                                            @ApiParam(value = "The location", example = "Milan", required = true)
                                            final String location,

                                            @RequestParam(required = false, defaultValue = "CELSIUS")
                                            @ApiParam(value = "Temperature unit")
                                            @Domain(enumClass = TemperatureUnit.class)
                                            final String temperatureUnit) {
        final WeatherWeekDTO weekForecast = new WeatherWeekDTO();
        weekForecast.setLocation(location);

        for (WeekDay weekday : WeekDay.values()) {
            weekForecast.getDays().put(
                weekday.name(),
                forecastService.getForecastsForDay(location, weekday, TemperatureUnit.valueOf(temperatureUnit))
            );
        }

        return weekForecast;
    }

    @GetMapping("/forecast/{location}/day/{weekday}")
    @ApiOperation(value = "Gets the daily forecast")
    public WeatherDaySample getDailyForecast(@PathVariable
                                             @ApiParam(value = "The location", example = "Milan", required = true)
                                             final String location,

                                             @PathVariable
                                             @ApiParam(value = "Day of the week")
                                             @Domain(enumClass = WeekDay.class)
                                             final String weekday,

                                             @RequestParam(required = false, defaultValue = "CELSIUS")
                                             @ApiParam(value = "Temperature unit")
                                             @Domain(enumClass = TemperatureUnit.class)
                                             final String temperatureUnit) {
        return forecastService.getForecastsForDay(location, WeekDay.valueOf(weekday), TemperatureUnit.valueOf(temperatureUnit));
    }
}
