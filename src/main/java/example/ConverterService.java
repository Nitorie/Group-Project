package example;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ConverterService {





    public Map getWeather(String city) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "b0a2a0d6d05a640a93ae2499cef16636")
                .queryParam("units", "metric")
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> weatherData = restTemplate.getForObject(url, Map.class);
        return weatherData;
    }

    public double convertToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
}
