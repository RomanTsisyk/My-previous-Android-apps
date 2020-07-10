package tsisyk.app.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import tsisyk.app.forecast.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)