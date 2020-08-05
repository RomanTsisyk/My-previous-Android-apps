package app.tsisyk.weather.ui

import app.tsisyk.weather.R
import app.tsisyk.weather.adapter.CityAdapter
import app.tsisyk.weather.viewModel.MainActivityViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        button.setOnClickListener {
            if (search_city.text!!.isNotEmpty())
                viewModel.searchLocation(search_city.text.toString())
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
        Log.i("namesListView", namesListView.toString())

    }
}
