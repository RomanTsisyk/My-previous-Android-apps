package tsisyk.app.desertandcandies.model

class DesertGereratior {

    fun generateDesert(attributes: DesertAttributes, name: String = "", drawable: Int = 0): Desert {
        val pointsRanking = 10 * attributes.price + 10 * attributes.calories + 10 * attributes.taste
        return Desert(attributes, pointsRanking, name, drawable)
    }
}