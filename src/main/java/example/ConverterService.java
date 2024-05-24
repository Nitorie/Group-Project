package example;


import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    public double convertToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
}
