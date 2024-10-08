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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WeatherForecastScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherForecastViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    if (items is WeatherForecastUiState.Empty) {
//        WeatherForecastScreen(
//            onSave = viewModel::addWeatherForecast,
//            modifier = modifier
//        )
//    }
    WeatherForecastScreen(
        uiState = uiState,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
internal fun WeatherForecastScreen(
    uiState: WeatherForecastUiState,
    viewModel: WeatherForecastViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        WeatherForecastSearchSection(modifier, onSave = viewModel::getWeatherForecast)
    }
    when (uiState) {

        is WeatherForecastUiState.Empty -> {
            Column(modifier) {
                WeatherForecastSearchSection(modifier, onSave = viewModel::getWeatherForecast)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                }
            }
        }

        is WeatherForecastUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WeatherForecastUiState.Error -> {
            Log.d("TAG", "WeatherForecastScreen: Error ${uiState.throwable}")
        }

        is WeatherForecastUiState.Success -> {
            Column(modifier) {
                WeatherForecastSearchSection(modifier, onSave = viewModel::getWeatherForecast)
                val items = uiState.data
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Saved item: $items")
                }
            }

        }
    }
}

@Composable
internal fun WeatherForecastSearchSection(modifier: Modifier, onSave: (name: String) -> Unit) {
    var cityName by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = cityName,
            onValueChange = {
                cityName = it
            }
        )

        Button(modifier = Modifier.width(96.dp), onClick = { onSave(cityName) }) {
            Text("Save")
        }
    }
}

// Previews

//@Preview(showBackground = true)
//@Composable
//private fun DefaultPreview() {
//    MyApplicationTheme {
//        WeatherForecastScreen(onSave = {}, modifier = modifier, viewModel = viewModel)
//    }
//}
//
//@Preview(showBackground = true, widthDp = 480)
//@Composable
//private fun PortraitPreview() {
//    MyApplicationTheme {
//        WeatherForecastScreen(/*items = City(
//            weather = listOf(),
//            main = City.Main(temp = 2.3, humidity = 8183),
//            wind = City.Wind(speed = 4.5f, deg = 1940),
//            name = "Wilmer Weiss"
//        ), */onSave = {})
//    }
//}
