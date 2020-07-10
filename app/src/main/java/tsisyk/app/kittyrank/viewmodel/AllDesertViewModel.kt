package tsisyk.app.kittyrank.viewmodel

import androidx.lifecycle.ViewModel
import tsisyk.app.kittyrank.model.DesertRepository
import tsisyk.app.kittyrank.model.room.RoomRepository

class AllDesertViewModel (private val repository: DesertRepository = RoomRepository()) :ViewModel() {

    private val getAllDesertsLiveData = repository.getAllDeserts()

    fun getAllDesertsLiveData() = getAllDesertsLiveData

    fun clearAllDeserts () = repository.clearAllDeserts()

}