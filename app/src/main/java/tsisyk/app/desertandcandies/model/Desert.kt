package tsisyk.app.desertandcandies.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desrt_table")
data class Desert(
        val attributes: DesertAttributes = DesertAttributes(),
        val pointsRanking: Int = 0,
        @PrimaryKey @NonNull
        val name: String,
        val drawable: Int = 0
)