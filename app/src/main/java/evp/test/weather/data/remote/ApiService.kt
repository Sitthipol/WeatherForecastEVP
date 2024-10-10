package evp.test.weather.data.remote

import evp.test.weather.data.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather/")
    suspend fun getCityData(
        @Query("q") q: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): City
}