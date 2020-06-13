package tsisyk.app.desertandcandies.model

object AttributeStore {
    val PRICE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("", 0))
        avatars.add(AttributeValue("less than", 5))
        avatars.add(AttributeValue("less than", 10))
        avatars.add(AttributeValue("less than", 20))
        avatars
    }
    val CALORIES: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("" , 0))
        avatars.add(AttributeValue("more than", 50))
        avatars.add(AttributeValue("more than", 100))
        avatars.add(AttributeValue("more than ", 500))
        avatars
    }
    val TASTE: List<AttributeValue> by lazy {
        val avatars = mutableListOf<AttributeValue>()
        avatars.add(AttributeValue("Fine",0))
        avatars.add(AttributeValue("Tasty", 1))
        avatars.add(AttributeValue("Delishes", 2))
        avatars.add(AttributeValue("Awesome", 3))
        avatars
    }
}