package tsisyk.app.kittyrank.model

object AttributeStore {
    val MY_OPINION: List<AttributeValue> by lazy {
        val myOpinion = mutableListOf<AttributeValue>()
        myOpinion.add(AttributeValue("Do you like her?", 0))
        myOpinion.add(AttributeValue("don`t like", 1))
        myOpinion.add(AttributeValue("don`t know", 2))
        myOpinion.add(AttributeValue("like", 3))
        myOpinion.add(AttributeValue("love", 4))
        myOpinion
    }
    val KITTIES_OPINION: List<AttributeValue> by lazy {
        val kitty_opinion = mutableListOf<AttributeValue>()
        kitty_opinion.add(AttributeValue("Does she like you?" , 0))
        kitty_opinion.add(AttributeValue("don`t lokis me" , 1))
        kitty_opinion.add(AttributeValue("don`t know", 1))
        kitty_opinion.add(AttributeValue("likes me", 4))
        kitty_opinion.add(AttributeValue("loves me", 7))
        kitty_opinion
    }
    val EMOTIONS: List<AttributeValue> by lazy {
        val emotions = mutableListOf<AttributeValue>()
        emotions.add(AttributeValue("Is she kind?",0))
        emotions.add(AttributeValue("Angry",-1))
        emotions.add(AttributeValue("Sad", 1))
        emotions.add(AttributeValue("Normal", 2))
        emotions.add(AttributeValue("Funny", 3))
        emotions.add(AttributeValue("Awersome", 4))
        emotions
    }

    val AGE: List<AttributeValue> by lazy {
        val age = mutableListOf<AttributeValue>()
        age.add(AttributeValue("How old is she?",0))
        age.add(AttributeValue("To young",2))
        age.add(AttributeValue("ok", 8))
        age.add(AttributeValue("To old", 3))

        age
    }
}