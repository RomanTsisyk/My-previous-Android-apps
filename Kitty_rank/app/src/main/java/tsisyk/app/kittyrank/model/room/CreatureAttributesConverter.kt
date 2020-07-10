package tsisyk.app.kittyrank.model.room

import androidx.room.TypeConverter
import tsisyk.app.kittyrank.model.DesertAttributes
import java.lang.Integer.*
import java.util.*

class DesertAttributesConverter {
    @TypeConverter
    fun fromDesertAttributes(attributes: DesertAttributes?): String? {
        if (attributes != null) {
            return String.format(Locale.US, "%d,%d,%d,%d" ,
                    attributes.my_opinion,
                    attributes.kitty_opinion,
                    attributes.emotion,
                    attributes.age)
        }
        return null
    }

    @TypeConverter
    fun toDesertAttributes(value: String?): DesertAttributes? {
        if (value != null) {
            val pieces = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return DesertAttributes(
                    my_opinion = parseInt(pieces[0]),
                    kitty_opinion = parseInt(pieces[1]),
                    emotion = parseInt(pieces[2]),
                    age = parseInt(pieces[3]))
        }
        return null
    }
}