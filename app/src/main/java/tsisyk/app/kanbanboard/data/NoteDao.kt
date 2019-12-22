package tsisyk.app.kanbanboard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun addMote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes(): List<Note>

    @Insert
    fun addMultipleNote(
        vararg note: Note
    )

}