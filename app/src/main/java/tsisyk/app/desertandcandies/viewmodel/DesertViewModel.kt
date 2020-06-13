package tsisyk.app.desertandcandies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tsisyk.app.desertandcandies.model.*
import tsisyk.app.desertandcandies.model.AttributeType.*

class DesertViewModel(private val generator: DesertGereratior = DesertGereratior()) : ViewModel() {
    private val desertLiveData = MutableLiveData<Desert>()
    fun getDesertLiveData(): LiveData<Desert> = desertLiveData

    var name = ""
    var price = 0
    var taste = 0
    var calories = 0
    var drawable = 0

    lateinit var desert: Desert

    fun updateDesert() {
        val attributes = DesertAttributes(price, calories, taste)
        desert = generator.generateDesert(attributes, name, drawable)
        desertLiveData.postValue(desert)
    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            PRICE -> price = AttributeStore.PRICE[position].value
            CALORIES -> calories = AttributeStore.CALORIES[position].value
            TASTE -> taste = AttributeStore.TASTE[position].value
        }
        updateDesert()
    }

    fun drawableSelected(drawable: Int){
        this.drawable = drawable
        updateDesert()

    }
}

