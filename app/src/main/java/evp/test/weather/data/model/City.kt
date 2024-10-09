package evp.test.weather.data.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("cod")
    val code: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String = ""
) {
    data class Main(
        @SerializedName("temp")
        val temp: Double = 0.0,
        @SerializedName("humidity")
        val humidity: Int = 0
    )

    data class Weather(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("icon")
        val icon: String = ""
    )
}
