package tsisyk.app.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import tsisyk.app.forecast.data.repository.ForecastRepository
import tsisyk.app.forecast.internal.UnitSystem
import tsisyk.app.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
