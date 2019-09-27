package tsisyk.app.forecast.data.response


data class CurrentWeatherRespond(
    val current: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)