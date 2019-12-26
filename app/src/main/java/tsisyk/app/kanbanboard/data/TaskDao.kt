package tsisyk.app.kanbanboard.data

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task?)

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAll(): List<Task>

    @Insert
    fun addMultyTask(vararg task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}