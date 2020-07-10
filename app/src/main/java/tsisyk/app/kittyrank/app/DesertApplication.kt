package tsisyk.app.kittyrank.app

import android.app.Application
import androidx.room.Room
import tsisyk.app.kittyrank.model.room.DesertDatabase

class DesertApplication : Application() {

    companion object {
        lateinit var database: DesertDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, DesertDatabase::class.java, "desert_database")
                .build()
    }
}