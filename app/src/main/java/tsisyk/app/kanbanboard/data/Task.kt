package tsisyk.app.kanbanboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Task (
    val title: String,
    val description: String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}