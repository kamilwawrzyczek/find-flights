package eu.wawrzyczek.findflights

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.flights.search.SearchClient
import eu.wawrzyczek.findflights.search.autocomplete.StationsClient
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val STATIC = "static"
const val API = "API"
private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"

val apiModule = module {
    single { RxJava2CallAdapterFactory.create() }
    single { GsonBuilder().setDateFormat(DEFAULT_DATE_FORMAT).create() }
    single { GsonConverterFactory.create(get()) }
    single { OkHttpClient.Builder().build() }
    single(STATIC) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.STATIC_URL)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }
    single(API) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single { get<Retrofit>(STATIC).create(StationsClient::class.java) }
    single { get<Retrofit>(API).create(SearchClient::class.java) }
}