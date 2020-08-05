package app.tsisyk.weather.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.tsisyk.weather.R
import app.tsisyk.weather.adapter.ForecastAdapter
import app.tsisyk.weather.viewModel.ForecastActivityViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_forecast.*
import kotlinx.android.synthetic.main.weather_detail.*
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
            if (it != null) {

                var name = it.title
                var allTemp =
                    df.format(it.consolidated_weather[0].min_temp) + " °C - " + df.format(it.consolidated_weather[0].max_temp) + " °C"
                var weatherStateName = it.consolidated_weather[0].weather_state_name

                var textSunRise = it.sun_rise.drop(11).dropLast(13)
                var textSunSet = it.sun_set.drop(11).dropLast(13)
                var timeZone = it.timezone
                var country = it.parent.title


                current_date.text = it.consolidated_weather[0].applicable_date
                cityName.text = name
                currentWeather_temp.text = allTemp
                Glide.with(this)
                    .load("https://www.metaweather.com/static/img/weather/png/${it.consolidated_weather[0].weather_state_abbr}.png")
                    .into(currentWeather_icon)

                current_weather_condition.text = weatherStateName
                text_sun_rise.text = textSunRise
                text_sun_set.text = textSunSet
                text_timezone.text = timeZone
                text_location_parent.text = country

//                currentWeather.setOnClickListener {
//                    val view = layoutInflater.inflate(R.layout.weather_detail, null, false)
//                    val popupWindow = PopupWindow(view, MATCH_PARENT, MATCH_PARENT)
//                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
//
//                    text_weather_state_name_pop_up =
//                    text_wind_direction_compass_pop_up
//                    text_min_temp_pop_up = textSunSet
//                    text_max_temp_pop_up
//                    text_wind_speed_pop_up
//                    text_wind_direction_pop_up
//                    text_air_pressure_pop_up
//                    text_humidity_pop_up
//                    text_visibility_pop_up
//                    text_predictability_pop_up
//                    text_title_pop_up
//
//                }
            }
        })

    }


}
