package tsisyk.app.desertandcandies.model


data class Desert(
        val attributes: DesertAttributes = DesertAttributes(),
        val pointsRanking: Int = 0,
        val name: String,
        val drawable: Int = 0
)