WeatherForecaseEVP app that show the weather of your search city. for detail that show on application is Name of city, condition of weather in icon, description, and tempurature in celsius by using openweathermap api.

Get API_KEY on website https://openweathermap.org/ and change your api key on Constants,kt file -> const val API_KEY = "YOUR_API_KEY"
you can change unit in standard, metric, imperial. Default is standard on Constants,kt file -> const val UNITS = "metric"
using coil for image loading

![Screen Shot 2567-10-10 at 12 18 36](https://github.com/user-attachments/assets/3e58574e-22f6-417d-be18-45ad6784fee1)

Structure of this project
- MVVM
- Jetpack compose

API
- openweathermap https://api.openweathermap.org/data/2.5/weather?q={city_name}&appid={api_key}&units={units}

Libraries
- Retrofit
- Hilt
- ViewModel
- Coil
- Navigation
- Jetpack Compose

![c6f75d03-dcf1-4a3e-90da-684b7f6864de](https://github.com/user-attachments/assets/829770c8-dbde-4464-a23d-1e520e1d7c9e) ![f0d37e21-206c-499e-8156-3a8410874e77](https://github.com/user-attachments/assets/c4864b70-4c33-447e-aed5-6fa8c08661a2)
