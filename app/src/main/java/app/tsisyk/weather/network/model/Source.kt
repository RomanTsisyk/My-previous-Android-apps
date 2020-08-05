package app.tsisyk.weather.network.model

data class Source(
    val crawl_rate: Int,
    val slug: String,
    val title: String,
    val url: String
)