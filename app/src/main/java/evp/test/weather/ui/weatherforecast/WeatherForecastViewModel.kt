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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import evp.test.weather.data.WeatherForecastRepository
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Error
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Loading
import evp.test.weather.ui.weatherforecast.WeatherForecastUiState.Success
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) : ViewModel() {

    val uiState: StateFlow<WeatherForecastUiState> = weatherForecastRepository
        .weatherForecasts.map<List<String>, WeatherForecastUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addWeatherForecast(name: String) {
        viewModelScope.launch {
            weatherForecastRepository.add(name)
        }
    }
}

sealed interface WeatherForecastUiState {
    object Loading : WeatherForecastUiState
    data class Error(val throwable: Throwable) : WeatherForecastUiState
    data class Success(val data: List<String>) : WeatherForecastUiState
}
