package tsisyk.app.forecast.data.repository

import androidx.lifecycle.LiveData
import tsisyk.app.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

}