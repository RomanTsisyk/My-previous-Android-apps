package tsisyk.app.kittyrank.model

data class AttributeValue(val name: String = "", val value: Int = 0) {
    override fun toString() = "$name"
}