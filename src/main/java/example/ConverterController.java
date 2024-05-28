package example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ConverterController {


//    @Autowired
    private ConverterService weatherService = new ConverterService();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        Map<String, Object> weatherData = weatherService.getWeather(city);
        model.addAttribute("city", city);
        model.addAttribute("weatherData", weatherData);
        return "weather";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam("celsius") double celsius, Model model) {
        double fahrenheit = weatherService.convertToFahrenheit(celsius);
        model.addAttribute("celsius", celsius);
        model.addAttribute("fahrenheit", fahrenheit);
        return "result";
    }
}
