package evp.test.weather.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import dagger.Provides
import evp.test.weather.data.remote.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getBaseUrl():String = "http://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun getRetrofitBuilder(baseUrl:String):Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}