package tsisyk.app.desertandcandies.model

object AttributeStore {
    val PRICE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("> 20 €"))
        avatars.add(AttributeValue("6-20 €", 2))
        avatars.add(AttributeValue("1-5 €", 3))
        avatars.add(AttributeValue("FREE", 4))
        avatars
    }
    val CALORIES: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("> 400" , 1))
        avatars.add(AttributeValue("200 ..400", 1))
        avatars.add(AttributeValue("100 ..199", 2))
        avatars.add(AttributeValue(" < 99", 3))
        avatars
    }
    val TASTE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("Fine",1))
        avatars.add(AttributeValue("Tasty", 1))
        avatars.add(AttributeValue("Delishes", 2))
        avatars.add(AttributeValue("Awesome", 3))
        avatars
    }
}