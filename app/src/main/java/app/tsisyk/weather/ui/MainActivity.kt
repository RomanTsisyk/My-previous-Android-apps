package app.tsisyk.weather.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color.BLUE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.tsisyk.weather.R
import app.tsisyk.weather.adapter.CityAdapter
import app.tsisyk.weather.viewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: CityAdapter
    var MY_KEY = "CITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        button.setOnClickListener {
            if (search_city.text!!.isNotEmpty()) {
                if (search_city.text.length > 2) {
                    viewModel.searchLocation(search_city.text.toString())
                }
            }
        }

        viewModel.showProgress.observe(this, Observer {
            if (it)
                progressBar.visibility = VISIBLE
            else
                progressBar.visibility = GONE
        })

        viewModel.locationList.observe(this, Observer {
            adapter.setLocationList(it)
        })

        adapter = CityAdapter(this)
        namesListView.adapter = adapter

        val sharedPref = this.getSharedPreferences("CITY.xml", Context.MODE_PRIVATE)
        val name = sharedPref.getString("CITY", "Warsaw")
        showSnackBar(name!!)
    }

    private fun showSnackBar(text: String) {
        var snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.part_1) + text + getString(R.string.part_2), 10_000
        )
        snackBar.setAction(getString(R.string.yes)) { _ ->
            val intent = Intent(this, ForecastActivity::class.java)
            startActivity(intent)
        }
        snackBar.setBackgroundTint(BLUE).show()

    }

}
