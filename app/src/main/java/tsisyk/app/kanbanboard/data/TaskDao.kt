package tsisyk.app.kanbanboard.data

import androidx.room.*

@Dao
interface TaskDao {


//    DONE, IN_PROGRESS, PEER_REVIREW, IN_TEST, BLOKED

    @Insert
    fun addTask(task: Task?)

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE state = 'DONE' ORDER BY id DESC")
    fun getAllDone(): List<Task>

    @Query("SELECT * FROM task WHERE state = 'IN_PROGRESS' ORDER BY id DESC")
    fun getIN_PROGRESS(): List<Task>

    @Query("SELECT * FROM task WHERE state = 'PEER_REVIREW' ORDER BY id DESC")
    fun getPEER_REVIREW(): List<Task>

    @Query("SELECT * FROM task WHERE state = 'IN_TEST' ORDER BY id DESC")
    fun getIN_TEST(): List<Task>

    @Query("SELECT * FROM task WHERE state = 'BLOKED' ORDER BY id DESC")
    fun getBLOKED(): List<Task>

    @Insert
    fun addMultyTask(vararg task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}