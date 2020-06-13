package tsisyk.app.desertandcandies.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import tsisyk.app.desertandcandies.app.DesertApplication
import tsisyk.app.desertandcandies.model.Desert
import tsisyk.app.desertandcandies.model.DesertRepository

class RoomRepository : DesertRepository {
    private val desertDao: DesertDao = DesertApplication.database.desertDao()

    private val allDeserts : LiveData<List<Desert>>

    init {
        allDeserts = desertDao.getAllDeserts()
    }

    private class InsertAsyncTask internal constructor(private val dao: DesertDao) : AsyncTask<Desert, Void, Void>() {
        override fun doInBackground(vararg params: Desert): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: DesertDao) : AsyncTask<Desert, Void, Void>() {
        override fun doInBackground(vararg params: Desert): Void? {
            dao.clearDeserts(*params)
            return null
        }
    }

    override fun saveDesert(desert: Desert) {
        InsertAsyncTask(desertDao).execute(desert)
    }

    override fun getAllDeserts() = allDeserts

    override fun clearAllDeserts() {
        val desertArray = allDeserts.value?.toTypedArray()
        if ( desertArray != null){
            DeleteAsyncTask(desertDao).execute(*desertArray)
        }
    }
}