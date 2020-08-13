/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tsisyk.app.rickandmorty.ui

import androidx.lifecycle.*
import tsisyk.app.rickandmorty.data.Repository
import tsisyk.app.rickandmorty.model.GetCharactersResult
import tsisyk.app.rickandmorty.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

/**
 * ViewModel for the [MainActivity] screen.
 * The ViewModel works with the [Repository] to get the data.
 */
@ExperimentalCoroutinesApi
class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<GetCharactersResult> = queryLiveData.switchMap { queryString ->
        liveData {
            EspressoIdlingResource.increment()
            val repos = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
            EspressoIdlingResource.decrement()

        }
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, totalItemCount: Int) {
        if (visibleItemCount + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}