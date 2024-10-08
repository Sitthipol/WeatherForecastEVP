/*
 * Copyright (C) 2022 The Android Open Source Project
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

package evp.test.weather.data

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.map
import evp.test.weather.data.local.database.WeatherForecast
import evp.test.weather.data.local.database.WeatherForecastDao
import evp.test.weather.data.model.City
import evp.test.weather.data.remote.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import evp.test.weather.common.asResult
import kotlin.jvm.Throws

interface WeatherForecastRepository {

//    suspend fun add(name: String)
    suspend fun getWeather(city: String): Flow<Result<City>>
}

class DefaultWeatherForecastRepository @Inject constructor(
    private val apiServiceImp: ApiServiceImp
) : WeatherForecastRepository {

//    override val weatherForecasts: Flow<List<String>> =
//        weatherForecastDao.getWeatherForecasts().map { items -> items.map { it.name } }

//    override suspend fun add(name: String) {
//        weatherForecastDao.insertWeatherForecast(WeatherForecast(name = name))
//    }

    override suspend fun getWeather(city: String): Flow<Result<City>> = flow {
        val response = apiServiceImp.getCity(city, "bd7748c052fa603662b78efb44ee277a")
        emit(Result.success(response))
    }
        .flowOn(Dispatchers.IO)
        .catch { e -> Result.failure<Throwable>(e) }
        .conflate()


}
