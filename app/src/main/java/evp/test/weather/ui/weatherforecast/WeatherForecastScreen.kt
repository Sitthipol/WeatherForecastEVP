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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import evp.test.weather.R
import evp.test.weather.ui.theme.MyApplicationTheme

@Composable
fun WeatherForecastScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherForecastViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isError by viewModel.isError.collectAsStateWithLifecycle()

    Column(modifier) {
        WeatherForecastSearchSection(
            viewModel::validateCityName,
            isError
        )
        WeatherForecastScreen(uiState)
    }

}

@Composable
internal fun WeatherForecastScreen(
    uiState: WeatherForecastUiState,
) {
    when (uiState) {

        is WeatherForecastUiState.Empty -> {

        }

        is WeatherForecastUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WeatherForecastUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState.errorMessage) {
                    ErrorMessage.ERROR_404 -> Text(text = stringResource(id = R.string.services_error_404))
                    ErrorMessage.INTERNET_NOT_CONNECT -> Text(text = stringResource(id = R.string.internet_not_connect))
                }
            }
        }

        is WeatherForecastUiState.Success -> {

            val items = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("${items?.name}")
                items?.weather?.map {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(50.dp),
                        model = stringResource(id = R.string.weather_condition_image, it.icon),
                        contentDescription = null,
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                    Text(it.description)
                }
                Text(stringResource(id = R.string.temp_in_celsius, items?.main?.temp.toString()))
            }

        }
    }
}

@Composable
internal fun WeatherForecastSearchSection(
    onValidate: (cityName: String) -> Unit,
    isError: Boolean
) {
    var cityName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = cityName,
            onValueChange = {
                cityName = it
            },
            maxLines = 1,
            label = { Text(text = stringResource(id = R.string.text_field_title)) },
            trailingIcon = {
                if (cityName.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { cityName = "" }
                    )
                }
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.text_field_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onValidate(cityName)
            }) {
            Text(stringResource(id = R.string.search_city_btn))
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        WeatherForecastScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        WeatherForecastScreen()
    }
}
