package tsisyk.app.rssfeed.model

data class Promotion(
    val feed: Feed,
    val items: List<Item>,
    val status: String
)