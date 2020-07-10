package tsisyk.app.rssfeed.Model


import com.google.gson.annotations.SerializedName

data class Feed(
    val author: String,
    val description: String,
    val image: String,
    val link: String,
    val title: String,
    val url: String
)