package app.tsisyk.weather.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.tsisyk.weather.R
import app.tsisyk.weather.adapter.ForecastAdapter
import app.tsisyk.weather.viewModel.ForecastActivityViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_forecast.*
import java.text.DecimalFormat

class ForecastActivity : AppCompatActivity() {

    private lateinit var viewModel: ForecastActivityViewModel
    private lateinit var adapter: ForecastAdapter
    var df: DecimalFormat = DecimalFormat("#.#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        viewModel = ViewModelProvider(this).get(ForecastActivityViewModel::class.java)

        if (intent.hasExtra("Location")) {
            // Do network callwa
            val location = intent.getIntExtra("Location", 0)
            if (location > 0)
                viewModel.getWeather(location)
        }

        viewModel.response.observe(this, Observer {
            adapter.getWeather(it.consolidated_weather)
        })

        adapter = ForecastAdapter(this)
        forecast.adapter = adapter

        viewModel.showProgress.observe(this, Observer {
            if (it)
                details_progress.visibility = VISIBLE
            else
                details_progress.visibility = GONE
        })

        viewModel.response.observe(this, Observer {
            if (it != null)
                current_date.text = it.consolidated_weather[0].applicable_date
            cityName.text = it.title
            currentWeather_temp.text =
                df.format(it.consolidated_weather[0].min_temp) + " °C - " + df.format(it.consolidated_weather[0].max_temp)+ " °C"
            Glide.with(this)
                .load("https://www.metaweather.com/static/img/weather/png/${it.consolidated_weather[0].weather_state_abbr}.png")
                .into(currentWeather_icon)

        })

    }
}
