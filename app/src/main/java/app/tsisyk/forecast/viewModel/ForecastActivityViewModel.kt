package app.tsisyk.forecast.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.tsisyk.forecast.network.model.WeatherResponse
import app.tsisyk.forecast.repository.ForecastActivityRepository

class ForecastActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ForecastActivityRepository(application)
    val showProgress: LiveData<Boolean>
    val response: LiveData<WeatherResponse>

    init {
        showProgress = repository.showProgress
        response = repository.response
    }

    fun getWeather(woeId: Int) {
        repository.getWeather(woeId)
    }


}