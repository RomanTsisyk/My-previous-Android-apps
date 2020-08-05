package app.tsisyk.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.tsisyk.weather.R
import app.tsisyk.weather.network.model.ConsolidatedWeather
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.waether_card_item.view.*
import java.text.DecimalFormat

class ForecastAdapter(private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder>() {
    private var list: List<ConsolidatedWeather> = ArrayList()
    var df: DecimalFormat = DecimalFormat("#.#")

    fun getWeather(list: List<ConsolidatedWeather>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size - 1
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.minTemp.text = list[position + 1].min_temp.toString() + " °C"
        holder.maxTemp.text = df.format(list[position + 1].max_temp).toString() + " °C"
        holder.applicableDate.text = list[position + 1].applicable_date
        holder.minTemp.text = df.format(list[position + 1].min_temp).toString()

        Glide.with(context)
            .load("https://www.metaweather.com/static/img/weather/png/64/${list[position].weather_state_abbr}.png")
            .into(holder.weatherIcon)
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
        var rootView = card.card_weather
    }

}