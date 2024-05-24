package example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConverterController {

    @Autowired
    private ConverterService converterService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam("celsius") double celsius, Model model) {
        double fahrenheit = converterService.convertToFahrenheit(celsius);
        model.addAttribute("celsius", celsius);
        model.addAttribute("fahrenheit", fahrenheit);
        return "result";
    }
}
