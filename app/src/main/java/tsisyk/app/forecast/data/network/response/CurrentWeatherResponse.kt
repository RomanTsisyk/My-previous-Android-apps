package tsisyk.app.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import tsisyk.app.forecast.data.db.entity.CurrentWeatherEntry
import tsisyk.app.forecast.data.db.entity.Location

data class CurrentWeatherResponse(
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)