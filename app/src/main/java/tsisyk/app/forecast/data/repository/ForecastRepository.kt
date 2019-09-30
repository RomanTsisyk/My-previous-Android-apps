package tsisyk.app.forecast.data.repository

import androidx.lifecycle.LiveData
import org.threeten.bp.LocalDate
import tsisyk.app.forecast.data.db.entity.WeatherLocation
import tsisyk.app.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import tsisyk.app.forecast.data.db.unitlocalized.UnitSpecificSimpleFutureWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>


}