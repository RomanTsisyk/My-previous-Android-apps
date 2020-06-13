package tsisyk.app.desertandcandies.model

import androidx.lifecycle.LiveData

interface DesertRepository {
    fun saveDesert( desert: Desert)
    fun getAllDeserts (): LiveData<List<Desert>>
    fun clearAllDeserts()
}