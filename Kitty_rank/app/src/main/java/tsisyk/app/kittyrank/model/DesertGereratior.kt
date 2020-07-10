package tsisyk.app.kittyrank.model

class DesertGereratior {

    fun generateDesert(attributes: DesertAttributes, name: String = "", drawable: Int = 0): Desert {
        val pointsRanking = 10 * attributes.my_opinion +
                10 * attributes.kitty_opinion +
                5 * attributes.emotion +
                3 * attributes.age
        return Desert(attributes, pointsRanking, name, drawable)
    }
}