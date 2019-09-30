package tsisyk.app.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import tsisyk.app.forecast.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer (
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)