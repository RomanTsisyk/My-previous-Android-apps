package tsisyk.app.desertandcandies.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tsisyk.app.desertandcandies.model.Desert

@Database(entities = [(Desert::class)] , version = 1)
@TypeConverters(DesertAttributesConverter::class)
abstract class DesertDatabase : RoomDatabase() {
    abstract fun desertDao(): DesertDao
}