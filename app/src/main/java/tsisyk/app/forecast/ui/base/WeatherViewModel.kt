package tsisyk.app.forecast.ui.base

import androidx.lifecycle.ViewModel
import tsisyk.app.forecast.data.provider.UnitProvider
import tsisyk.app.forecast.data.repository.ForecastRepository
import tsisyk.app.forecast.internal.UnitSystem
import tsisyk.app.forecast.internal.lazyDeferred


abstract class WeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}