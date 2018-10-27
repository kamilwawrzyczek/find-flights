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

val applicationModule = module {
    single { DateProvider() }
    single { AppNavigator(androidContext()) }
}