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

package evp.test.weather.ui.weatherforecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import evp.test.weather.data.WeatherForecastRepository
import evp.test.weather.data.model.City
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Error
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Loading
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherForecastUiState> =
        MutableStateFlow(WeatherForecastUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private fun getWeatherForecast(cityName: String) {
        viewModelScope.launch {
            weatherForecastRepository.getWeather(cityName)
                .onStart {
                    _uiState.value = Loading
                }
                .catch { e ->
                    when (e) {
                        is HttpException -> {
                            when (e.code()) {
                                404 -> _uiState.value = Error("City not found ")
                            }
                        }
                        is IOException -> _uiState.value = Error("Couldn't reach server. Check your internet connection.")
                    }
                }
                .collect { result ->
                    _uiState.value = Success(result)
                }
        }
    }

    fun validateCityName(cityName: String) {
        val isError = cityName.isBlank()
        _isError.value = isError

        if (!isError) {
            getWeatherForecast(cityName)
        }
    }
}

sealed interface WeatherForecastUiState {
    object Empty : WeatherForecastUiState
    object Loading : WeatherForecastUiState
    data class Error(val errorMessage: String?) : WeatherForecastUiState
    data class Success(val data: City?) : WeatherForecastUiState
}
