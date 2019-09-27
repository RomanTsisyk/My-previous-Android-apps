package tsisyk.app.forecast.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import tsisyk.app.forecast.data.response.CurrentWeatherRespond

const val API_KEY = "e0ba33334a42184dc43aad18215724a6"

interface WeatherApiService {

    @GET("current,json")
    fun getCurrentWeathe(
        @Query("q") location: String
    ): Deferred<CurrentWeatherRespond>

    companion object {
        operator fun invoke(): WeatherApiService {
            WeatherApiService()
            val requestInterceptor = Interceptor { chain ->
                val url =
                    chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.apixu/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}
