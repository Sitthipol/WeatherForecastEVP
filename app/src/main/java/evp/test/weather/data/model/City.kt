package evp.test.weather.data.model

data class City(
    val weather:List<Weather>,
    val main:Main,
    val wind:Wind,
    val name:String = ""
) {
    data class Main(
        val temp:Double = 0.0,
        val humidity:Int = 0
    )

    data class Weather(
        val description:String = "",
        val icon:String = ""
    )

    data class Wind (
        val speed:Float =0.0f,
        val deg:Int = 0
    )
}
