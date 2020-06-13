package tsisyk.app.desertandcandies.model.room

import androidx.room.RoomDatabase

abstract class DesertDatabase : RoomDatabase() {
    abstract fun desertDao(): DesertDao
}