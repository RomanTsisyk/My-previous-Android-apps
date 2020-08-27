package app.tsisyk.forecast.viewModel

import app.tsisyk.forecast.network.model.Location
import app.tsisyk.forecast.repository.MainActivityRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository  = MainActivityRepository(application)
    val showProgress : LiveData<Boolean>
    val locationList : LiveData<List<Location>>

    init {
        this.showProgress = repository.showProgress
        this.locationList = repository.locationList
    }


    fun searchLocation(searchString: String){
        repository.searchLocation(searchString)
    }
}