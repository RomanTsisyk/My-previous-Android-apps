package tsisyk.app.kanbanboard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun addMultyTask(
        vararg task: Task
    )

}