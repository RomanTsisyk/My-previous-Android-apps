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

package tsisyk.app.rickandmorty.data

import android.util.Log
import tsisyk.app.rickandmorty.api.ApiService
import tsisyk.app.rickandmorty.model.GetCharactersResult
import tsisyk.app.rickandmorty.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

/**
 * Repository class that works with local and remote data sources.
 */
@ExperimentalCoroutinesApi
class Repository(private val service: ApiService) {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Result>()

    // keep channel of results. The channel allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = ConflatedBroadcastChannel<GetCharactersResult>()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search characters whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun getSearchResultStream(query: String): Flow<GetCharactersResult> {
        Log.d("Search for character", "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)
        return searchResults.asFlow()
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

//    suspend fun retry(query: String) {
//        if (isRequestInProgress) return
//        requestAndSaveData(query)
//    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response = service.loadData(lastRequestedPage)
            val repos = response.results
            inMemoryCache.addAll(repos)
            val reposByName = reposByName(query)
            searchResults.offer(GetCharactersResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(GetCharactersResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(GetCharactersResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<Result> {
        // from the in memory cache select only the repos whose name or description matches
        // the query. Then order the results.
        return inMemoryCache
    }

}


