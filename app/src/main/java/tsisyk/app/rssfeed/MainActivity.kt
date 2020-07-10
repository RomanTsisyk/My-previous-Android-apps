@file:Suppress("DEPRECATION")

package tsisyk.app.rssfeed

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import tsisyk.app.rssfeed.adapter.FeedAdapter
import tsisyk.app.rssfeed.common.HTTPDataHandler
import tsisyk.app.rssfeed.medium_model.Medium




@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    lateinit var rss: Medium

    //RSS link
    private val rssLink = "https://www.wroclaw.pl/rss?alias=wiadomosci"
    private val convertLink = "https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.subtitle = "wroclaw.pl/wiadomości"

        recyclerView = findViewById(R.id.recycleView)
        val linearLayoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.layoutManager = linearLayoutManager

        loadRSS()
    }

    private fun loadRSS() {
        val loadRSSAsync = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, String, String>() {

            var mDialog = ProgressDialog(this@MainActivity)

            override fun onPreExecute() {
                mDialog.setMessage("Please wait...")
                mDialog.show()
            }

            override fun doInBackground(vararg params: String): String? {
                val result: String?
                val http = HTTPDataHandler()
                result = http.GetHTTPData(params[0])
                return result
            }


            override fun onPostExecute(string: String) {
                mDialog.dismiss()
                rss = Gson().fromJson(string, Medium::class.java)
                val adapter = FeedAdapter(rss, baseContext)
                recyclerView?.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        val stringBuilder = StringBuilder(convertLink)
        stringBuilder.append(rssLink)
        loadRSSAsync.execute(stringBuilder.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_refresh)
            loadRSS()
        return true
    }
}
