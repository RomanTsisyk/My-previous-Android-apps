package tsisyk.app.forecast

import android.content.Context
import android.content.Intent
import android.graphics.Color.*
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tsisyk.app.forecast.R
import tsisyk.app.forecast.adapter.CityAdapter
import tsisyk.app.forecast.viewModel.MainActivityViewModel
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
//                if (search_city.text.length > 2) {
                viewModel.searchLocation(search_city.text.toString())
//                }
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

        /** call adapter for posible city names
         * */

        adapter = CityAdapter(this)
        namesListView.adapter = adapter

        /** name = name of last searched city
        If  shared preference is empty name = "null"
         **/

        val sharedPref = this.getSharedPreferences("CITY.xml", Context.MODE_PRIVATE)
        val name = sharedPref.getString("CITY", "null")
        showSnackBar(name!!)
    }

    private fun showSnackBar(text: String) {

        /** if name == "null" -> show snakbar for first time user
         *
         * if name is not null (containce name of some city) ->
         * show snack back and ask to repeat previos search once more
         **/
        if (text !== "null") {
            var snackBar = Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.part_1) + " " + text + "\n" + getString(R.string.part_2), 5_000
            )
            snackBar.setAction(getString(R.string.yes)) { _ ->
                val intent = Intent(this, ForecastActivity::class.java)
                startActivity(intent)
            }
            snackBar.setTextColor(BLACK).setBackgroundTint(YELLOW).show()

        } else {
            var snackBar = Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.welcome), 2_000
            )
            snackBar.setTextColor(BLUE).setBackgroundTint(WHITE).show()
        }

    }

}
