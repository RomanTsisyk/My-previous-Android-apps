package tsisyk.app.forecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tsisyk.app.forecast.data.db.entity.CurrentWeatherEntry
import tsisyk.app.forecast.data.db.entity.FutureWeatherEntry
import tsisyk.app.forecast.data.db.entity.WeatherLocation


@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(tsisyk.app.forecast.data.db.LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): tsisyk.app.forecast.data.db.CurrentWeatherDao
    abstract fun futureWeatherDao(): tsisyk.app.forecast.data.db.FutureWeatherDao
    abstract fun weatherLocationDao(): tsisyk.app.forecast.data.db.WeatherLocationDao

    companion object {
        @Volatile private var instance: tsisyk.app.forecast.data.db.ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = tsisyk.app.forecast.data.db.ForecastDatabase.Companion.instance
                ?: synchronized(tsisyk.app.forecast.data.db.ForecastDatabase.Companion.LOCK) {
            tsisyk.app.forecast.data.db.ForecastDatabase.Companion.instance
                    ?: tsisyk.app.forecast.data.db.ForecastDatabase.Companion.buildDatabase(context).also { tsisyk.app.forecast.data.db.ForecastDatabase.Companion.instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    tsisyk.app.forecast.data.db.ForecastDatabase::class.java, "futureWeatherEntries.db")
                    .build()
    }
}