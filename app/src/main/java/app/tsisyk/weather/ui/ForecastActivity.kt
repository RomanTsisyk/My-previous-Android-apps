package app.tsisyk.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.tsisyk.weather.R
import app.tsisyk.weather.R.string.*
import app.tsisyk.weather.adapter.ForecastAdapter
import app.tsisyk.weather.adapter.validColor
import app.tsisyk.weather.viewModel.ForecastActivityViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_forecast.*
import kotlinx.android.synthetic.main.weather_detail.*
import java.text.DecimalFormat


@SuppressLint("SetTextI18n")
class ForecastActivity : AppCompatActivity() {

    private lateinit var viewModel: ForecastActivityViewModel
    private lateinit var adapter: ForecastAdapter
    var df: DecimalFormat = DecimalFormat("#.#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        viewModel = ViewModelProvider(this).get(ForecastActivityViewModel::class.java)

        if (intent.hasExtra("Location")) {
            // Do network call
            val location = intent.getIntExtra("Location", 0)
            val locationName = intent.getStringExtra("LocationName")
            if (location > 0)
                viewModel.getWeather(location)

            if (locationName.length > 3 ) {
                val preferences = getSharedPreferences("CITY.xml", Context.MODE_PRIVATE)?: return
                with(preferences.edit()) {
                    putString("CITY", locationName)
                    putInt("CITY_woeid", location)
                    apply()
                    Log.i( "NAME" , locationName)
                }
            }
        } else {
            val sharedPref = this.getSharedPreferences("CITY.xml", Context.MODE_PRIVATE)
            val location = sharedPref.getInt("CITY_woeid", 0)
            if (location > 0)
                viewModel.getWeather(location)
        }

        initViewModel()
        adapter = ForecastAdapter(this)
        forecast.adapter = adapter
        getResponse()
    }

    private fun initViewModel() {
        viewModel.response.observe(this, Observer {
            adapter.getWeather(it.consolidated_weather)
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                details_progress.visibility = VISIBLE
            else
                details_progress.visibility = GONE
        })
    }

    private fun getResponse() {
        viewModel.response.observe(this, Observer {
            if (it != null) {

                cityName.text = it.title

                current_date.text = it.consolidated_weather[0].applicable_date

                currentWeather_temp_min.text =
                    "${df.format(it.consolidated_weather[0].min_temp)} °C "

                currentWeather_temp_max.text =
                    "${df.format(it.consolidated_weather[0].max_temp)} °C"

                current_weather_condition.text = it.consolidated_weather[0].weather_state_name
                text_sun_rise.text = getString(sunrise) + it.sun_rise.drop(11).dropLast(13)
                text_sun_set.text = getString(sunset) + it.sun_set.drop(11).dropLast(13)
                text_timezone.text = it.timezone
                text_location_parent.text = it.parent.title

                Glide.with(this)
                    .load(
                        "https://www.metaweather.com/static/img/weather/png/" +
                                "${it.consolidated_weather[0].weather_state_abbr}.png"
                    )
                    .into(currentWeather_icon)

                currentWeather.setOnClickListener {
                    if (forecast.visibility == VISIBLE) {
                        forecast.visibility = GONE
                        city_information.visibility = GONE
                        inflateView.visibility = VISIBLE
                    } else {
                        forecast.visibility = VISIBLE
                        city_information.visibility = VISIBLE
                        inflateView.visibility = GONE
                    }
                }

                text_wind_direction_compass_pop_up.text =
                    getString(wind_direction_compass) +
                            it.consolidated_weather[0].wind_direction_compass

                text_wind_speed_pop_up.text = getString(wind_speed) +
                        df.format(it.consolidated_weather[0].wind_speed).toString()

                text_wind_direction_pop_up.text = getString(wind_directio) +
                        df.format(it.consolidated_weather[0].wind_direction).toString()

                text_air_pressure_pop_up.text = getString(air_pressure) +
                        df.format(it.consolidated_weather[0].air_pressure).toString()

                text_humidity_pop_up.text =
                    getString(humidity) +
                            df.format(it.consolidated_weather[0].humidity).toString()

                text_visibility_pop_up.text =
                    getString(visibility) +
                            df.format(it.consolidated_weather[0].visibility).toString()

                text_predictability_pop_up.text = getString(predictability) +
                        df.format(it.consolidated_weather[0].predictability).toString()

                validColor(currentWeather_temp_max)
                validColor(currentWeather_temp_min)
            }
        })
    }

}
