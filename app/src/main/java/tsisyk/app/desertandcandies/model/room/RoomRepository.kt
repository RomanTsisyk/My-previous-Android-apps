package tsisyk.app.desertandcandies.model.room

import android.os.AsyncTask
import tsisyk.app.desertandcandies.app.DesertApplication
import tsisyk.app.desertandcandies.model.Desert
import tsisyk.app.desertandcandies.model.DesertRepository

class RoomRepository : DesertRepository {
    private val desertDao: DesertDao = DesertApplication.database.desertDao()

    private class InsertAsyncTask internal constructor(private val dao: DesertDao) : AsyncTask<Desert, Void, Void>() {
        override fun doInBackground(vararg params: Desert): Void? {
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: DesertDao) : AsyncTask<Desert, Void, Void>() {
        override fun doInBackground(vararg params: Desert): Void? {
            return null
        }
    }
}