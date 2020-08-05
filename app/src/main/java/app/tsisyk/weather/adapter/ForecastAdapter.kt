package app.tsisyk.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.tsisyk.weather.R
import app.tsisyk.weather.network.model.ConsolidatedWeather
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.waether_card_item.view.*
import java.text.DecimalFormat

@Suppress("DEPRECATION")
class ForecastAdapter(private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder>() {
    private var list: List<ConsolidatedWeather> = ArrayList()
    private var df: DecimalFormat = DecimalFormat("#.#")
    private var BASE_URL = "https://www.metaweather.com/static/img/weather/png/64/"

    fun getWeather(list: List<ConsolidatedWeather>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size - 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.minTemp.text = "${df.format(list[position + 1].min_temp)} °C"
        holder.maxTemp.text = "${df.format(list[position + 1].max_temp)} °C"
        holder.weatherHumidity.text = "${(list[position + 1].humidity)} %"
        holder.applicableDate.text = list[position + 1].applicable_date


        Glide.with(context)
            .load("$BASE_URL${list[position].weather_state_abbr}.png")
            .into(holder.weatherIcon)
        validColor(holder.minTemp)
        validColor(holder.maxTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.waether_card_item,
                parent,
                false
            )
        )
    }

    class WeatherViewHolder(card: View) : RecyclerView.ViewHolder(card) {
        var date = card.weather_date
        var minTemp = card.weather_min_temp
        var maxTemp = card.weather_max_temp
        var applicableDate = card.weather_date
        var weatherIcon = card.weather_icon
        var weatherHumidity = card.weather_humidity
        var rootView = card.card_weather
    }


}

fun validColor(view: TextView) {
    var temp = view.text.toString().dropLast(3).toDouble()
    when {
        (temp <= 20) -> view.setTextColor(BLUE)
        (temp in 10..20) -> view.setTextColor(BLACK)
        (20 < temp) -> view.setTextColor(RED)
    }
}

