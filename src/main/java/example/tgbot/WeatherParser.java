package example.tgbot;

import org.json.JSONObject;

public class WeatherParser {
    public String parseWeatherData(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("location") || !jsonObject.has("current")) {
            return "Помилка: Невірний формат відповіді JSON";
        }

        JSONObject location = jsonObject.getJSONObject("location");
        JSONObject current = jsonObject.getJSONObject("current");

        String cityName = location.optString("name", "N/A");
        String region = location.optString("region", "N/A");
        String country = location.optString("country", "N/A");
        String localtime = location.optString("localtime", "N/A");

        double tempCelsius = current.optDouble("temp_c", Double.NaN);
        double feelsLikeCelsius = current.optDouble("feelslike_c", Double.NaN);
        int humidity = current.optInt("humidity", -1);
        double windKph = current.optDouble("wind_kph", Double.NaN);
        String windDir = current.optString("wind_dir", "N/A");
        double pressureMb = current.optDouble("pressure_mb", Double.NaN);
        double precipMm = current.optDouble("precip_mm", Double.NaN);
        String conditionText = current.optJSONObject("condition").optString("text", "N/A");

        return "Дані про погоду для " + cityName + ", " + region + ", " + country + ":\n" +
                "Місцевий час: " + localtime + "\n" +
                "Температура: " + tempCelsius + "°C\n" +
                "Відчувається як: " + feelsLikeCelsius + "°C\n" +
                "Погодні умови: " + conditionText + "\n" +
                "Вологість: " + humidity + "%\n" +
                "Вітер: " + windKph + " км/год " + windDir + "\n" +
                "Тиск: " + pressureMb + " мбар\n" +
                "Опади: " + precipMm + " мм";
    }
}
