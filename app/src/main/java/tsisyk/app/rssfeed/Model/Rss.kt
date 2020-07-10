package tsisyk.app.rssfeed.Model


import tsisyk.app.rssfeed.Model.Feed
import tsisyk.app.rssfeed.Model.Item

data class Rss(
    val feed: Feed,
    val items: List<Item>,
    val status: String
)