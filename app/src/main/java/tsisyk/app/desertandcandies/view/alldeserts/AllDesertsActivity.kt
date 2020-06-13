package tsisyk.app.desertandcandies.view.alldeserts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import tsisyk.app.desertandcandies.view.desert.DesertActivity
import kotlinx.android.synthetic.main.activity_all_desertss.*
import kotlinx.android.synthetic.main.content_all_desertss.*
import tsisyk.app.desertandcandies.R

class AllDesertsActivity : AppCompatActivity() {

    private val adapter = DesertAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_desertss)
        setSupportActionBar(toolbar)

        desertsRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        desertsRecyclerView.adapter = adapter

        fab.setOnClickListener {
            startActivity(Intent(this, DesertActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
