package tsisyk.app.diary.utilities

object TimeManager {

    fun getTime(currentTime: Long): String {
        val time = Integer.valueOf(currentTime.toInt())
        val h = (time.toLong() / 3600).toInt()
        val m = (time.toLong() - h * 3600).toInt() / 60
        val s = (time.toLong() - (h * 3600).toLong() - (m * 60).toLong()).toInt()
        return (if (h < 10) "0$h" else h).toString() + ":" +
                (if (m < 10) "0$m" else m) + ":" +
                if (s < 10) "0$s" else s
    }
}
