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

import evp.test.weather.data.model.City
import evp.test.weather.data.remote.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface WeatherForecastRepository {
    suspend fun getWeather(city: String): Flow<City>
}

class DefaultWeatherForecastRepository @Inject constructor(
    private val apiServiceImp: ApiServiceImp
) : WeatherForecastRepository {

    override suspend fun getWeather(city: String): Flow<City> = flow {
        val response = apiServiceImp.getCity(city, "bd7748c052fa603662b78efb44ee277a")
        emit(response)
    }
        .flowOn(Dispatchers.IO)
        .conflate()
}
