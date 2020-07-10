package tsisyk.app.kittyrank.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tsisyk.app.kittyrank.model.*
import tsisyk.app.kittyrank.model.AttributeType.*
import tsisyk.app.kittyrank.model.room.RoomRepository

class DesertViewModel(private val generator: DesertGereratior = DesertGereratior(),
                      private val repository: DesertRepository = RoomRepository()) : ViewModel() {

    private val desertLiveData = MutableLiveData<Desert>()
    fun getDesertLiveData(): LiveData<Desert> = desertLiveData

    private val saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData

    var name = ObservableField("")
    var opinion = 0
    var omoji = 0
    var kitty_opinion = 0
    var age = 0
    var drawable = 0

    lateinit var desert: Desert

    fun updateDesert() {
        val attributes = DesertAttributes(opinion, kitty_opinion, omoji, age)
        desert = generator.generateDesert(attributes, name.get() ?: "", drawable)
        desertLiveData.postValue(desert)
    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            MY_OPINION -> opinion = AttributeStore.MY_OPINION[position].value
            KITTY_OPINION -> kitty_opinion = AttributeStore.KITTIES_OPINION[position].value
            EMOJI -> omoji = AttributeStore.EMOTIONS[position].value
            AGE -> age = AttributeStore.AGE[position].value
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
            return opinion != 0 &&
                    omoji != 0 &&
                    kitty_opinion != 0 &&
                    name.isNotEmpty() &&
                    drawable != 0 &&
                    age!= null
        }
        return false

    }

    fun saveDesert() {
        return if (canSaveDesert()) {
            repository.saveDesert(desert)
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }
}

