package tsisyk.app.desertandcandies.model

object AttributeStore {
    val PRICE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("", 0))
        avatars.add(AttributeValue("€", 5))
        avatars.add(AttributeValue("€", 10))
        avatars.add(AttributeValue("€", 20))
        avatars
    }
    val CALORIES: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("" , 0))
        avatars.add(AttributeValue("cal", 50))
        avatars.add(AttributeValue("cal", 100))
        avatars.add(AttributeValue("cal", 500))
        avatars
    }
    val TASTE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("Fine",0))
        avatars.add(AttributeValue("Star", 1))
        avatars.add(AttributeValue("Star", 2))
        avatars.add(AttributeValue("Star", 3))
        avatars.add(AttributeValue("Star", 4))
        avatars.add(AttributeValue("Star", 5))
        avatars
    }
}