package tsisyk.app.desertandcandies.view.alldeserts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import tsisyk.app.desertandcandies.view.desert.DesertActivity
import kotlinx.android.synthetic.main.activity_all_desertss.*
import kotlinx.android.synthetic.main.content_all_desertss.*
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.viewmodel.AllDesertViewModel

class AllDesertsActivity : AppCompatActivity() {

    private val adapter = DesertAdapter(mutableListOf())

    private lateinit var viewModel: AllDesertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_desertss)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(AllDesertViewModel::class.java)

        desertsRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        desertsRecyclerView.adapter = adapter

        viewModel.getAllDesertsLiveData().observe(this, Observer
        { deserts ->
            deserts?.let {
                adapter.updateDeserts(deserts)
            }
        })

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
                viewModel.clearAllDeserts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
