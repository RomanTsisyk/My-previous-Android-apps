package tsisyk.app.forecast.data.repository

import androidx.lifecycle.LiveData
import tsisyk.app.forecast.data.db.entity.WeatherLocation
import tsisyk.app.forecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tsisyk.app.forecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import tsisyk.app.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate


interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}