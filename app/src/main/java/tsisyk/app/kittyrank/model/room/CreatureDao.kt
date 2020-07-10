package tsisyk.app.kittyrank.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import tsisyk.app.kittyrank.model.Desert
import java.text.ParsePosition

@Dao
interface DesertDao{

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    fun insert( desert: Desert)

    @Delete
    fun clearDeserts( vararg desert: Desert)

    @Query("SELECT * FROM  desrt_table ")
    fun getAllDeserts(): LiveData<List<Desert>>

//    @Query("SELECT * FROM  desrt_table ORDER BY name")
//    fun getAllDesertsSortByNamr(): LiveData<List<Desert>>
}