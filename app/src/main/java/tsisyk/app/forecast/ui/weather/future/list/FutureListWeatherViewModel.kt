package tsisyk.app.forecast.ui.weather.future.list

import androidx.lifecycle.ViewModel;
import org.threeten.bp.LocalDate
import tsisyk.app.forecast.data.provider.UnitProvider
import tsisyk.app.forecast.data.repository.ForecastRepository
import tsisyk.app.forecast.internal.lazyDeferred
import tsisyk.app.forecast.ui.base.WeatherViewModel

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}
