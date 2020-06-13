package tsisyk.app.desertandcandies.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import tsisyk.app.desertandcandies.model.*
import tsisyk.app.desertandcandies.model.AttributeType.*
import tsisyk.app.desertandcandies.model.room.RoomRepository

class DesertViewModel(private val generator: DesertGereratior = DesertGereratior(),
                      private val repository: DesertRepository = RoomRepository()) : ViewModel() {

    private val desertLiveData = MutableLiveData<Desert>()
    fun getDesertLiveData(): LiveData<Desert> = desertLiveData

    private val saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData


    var name = ObservableField<String>("")
    var price = 0
    var taste = 0
    var calories = 0
    var drawable = 0

    lateinit var desert: Desert

    fun updateDesert() {
        val attributes = DesertAttributes(price, calories, taste)
        desert = generator.generateDesert(attributes, name.get() ?: "", drawable)
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

    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateDesert()

    }

    fun canSaveDesert(): Boolean {
        val name = this.name.get()
        name?.let {
            return price != 0 && taste != 0 && calories != 0 && drawable != 0 && name.isNotEmpty()
        }
        return false
    }

    fun saveDesert() {
        return if (canSaveDesert()) {
            repository.saveDesert(desert)
            saveLiveData.postValue(true)
        } else saveLiveData.postValue(false)
    }
}

