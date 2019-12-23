package tsisyk.app.kanbanboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    val title: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}