package eu.wawrzyczek.findflights

import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.search.autocomplete.StationsClient
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val STATIC = "static"

val applicationModule = module {
    single { DateProvider() }
    single { AppNavigator(androidContext()) }
    single { RxJava2CallAdapterFactory.create() }
    single { GsonConverterFactory.create() }
    single { OkHttpClient.Builder().build() }
    single(STATIC) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.STATIC_URL)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }
    single { get<Retrofit>(STATIC).create(StationsClient::class.java) }
}