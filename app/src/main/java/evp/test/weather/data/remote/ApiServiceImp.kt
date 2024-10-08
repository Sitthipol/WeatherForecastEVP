package evp.test.weather.data.remote

import evp.test.weather.data.model.City
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {

    suspend fun getCity(city: String, appId: String): City =
        apiService.getCityData(city, appId)
}