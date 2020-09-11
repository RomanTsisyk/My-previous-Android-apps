package tsisyk.app.rssfeed.common

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class HTTPDataHandler {
    fun GetHTTPData(urlString: String?): String? {
        try {
            val url = URL(urlString)
            val urlConnection = url.openConnection() as HttpURLConnection
            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val `in`: InputStream = BufferedInputStream(urlConnection.inputStream)
                val r = BufferedReader(InputStreamReader(`in`))
                val sb = StringBuilder()
                var line: String?
                while (r.readLine().also { line = it } != null) sb.append(line)
                stream = sb.toString()
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stream
    }

    companion object {
        var stream: String? = null
    }
}