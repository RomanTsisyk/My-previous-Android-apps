package tsisyk.app.desertandcandies.app

import android.app.Application
import tsisyk.app.desertandcandies.model.room.DesertDatabase

class DesertApplication : Application() {

    companion object {
        lateinit var database: DesertDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // TODO: init database
    }
}