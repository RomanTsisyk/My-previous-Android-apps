package tsisyk.app.rickandmorty.model

data class Result(
        val gender: String,
        val id: Int,
        val image: String,
        val location: Location,
        val name: String,
        val origin: Origin,
        val status: String,
        val url: String
)