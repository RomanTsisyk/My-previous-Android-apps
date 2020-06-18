package tsisyk.app.desertandcandies.model.room

import androidx.room.TypeConverter
import tsisyk.app.desertandcandies.model.DesertAttributes
import java.lang.Integer.*
import java.util.*

class DesertAttributesConverter {
    @TypeConverter
    fun fromDesertAttributes(attributes: DesertAttributes?): String? {
        if (attributes != null) {
            return String.format(Locale.US, "%d,%d,%d", attributes.price, attributes.calories, attributes.taste)
        }
        return null
    }

    @TypeConverter
    fun toDesertAttributes(value: String?): DesertAttributes? {
        if (value != null) {
            val pieces = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return DesertAttributes(
                    price = parseInt(pieces[0]),
                    calories = parseInt(pieces[1]),
                    taste = parseInt(pieces[2]))
        }
        return null
    }
}