package tsisyk.app.forecast.ui.weather.current

import tsisyk.app.forecast.data.provider.UnitProvider
import tsisyk.app.forecast.data.repository.ForecastRepository
import tsisyk.app.forecast.internal.lazyDeferred
import tsisyk.app.forecast.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}
