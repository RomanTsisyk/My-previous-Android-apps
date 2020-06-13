package tsisyk.app.desertandcandies.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import tsisyk.app.desertandcandies.model.Desert

@Dao
interface DesertDao{

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    fun insert( desert: Desert)

    @Delete
    fun clearDeserts( vararg desert: Desert)

    @Query("SELECT * FROM  desrt_table ORDER BY name ASC")
    fun getAllDeserts(): LiveData<List<Desert>>
}