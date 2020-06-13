package tsisyk.app.desertandcandies.viewmodel

import androidx.lifecycle.ViewModel
import tsisyk.app.desertandcandies.model.DesertRepository
import tsisyk.app.desertandcandies.model.room.RoomRepository

class AllDesertViewModel (private val repository: DesertRepository = RoomRepository()) :ViewModel() {

    private val getAllDesertsLiveData = repository.getAllDeserts()

    fun getAllDesertsLiveData() = getAllDesertsLiveData

    fun clearAllDeserts () = repository.clearAllDeserts()

}