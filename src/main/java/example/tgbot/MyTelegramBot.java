package example.tgbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MyTelegramBot extends TelegramLongPollingBot {
    private boolean waitingForCity = false;
    private boolean waitingForMyCity = false;
    private boolean waitingForTempConversion = false;
    private final Map<Long, String> userCities = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "StormWatcher_bot"; // Replace with your bot's username
    }

    @Override
    public String getBotToken() {
        return "7225651729:AAHXWm1ZLBUHm4R9_CfI7OCggBkoVYPpgzU"; // Replace with your bot's token
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));

            if (messageText.equals("/start")) {
                message.setText("Оберіть доступні команди у меню.");
            } else if (messageText.equals("/weather")) {
                waitingForCity = true;
                message.setText("Напишіть назву вашого міста:");
            } else if (waitingForCity) {
                waitingForCity = false;
                String cityName = messageText;

                try {
                    example.tgbot.WeatherService weatherService = new example.tgbot.WeatherService();
                    String weatherData = weatherService.getWeatherData(cityName);
                    WeatherParser weatherParser = new WeatherParser();
                    String weatherInfo = weatherParser.parseWeatherData(weatherData);
                    message.setText(weatherInfo);
                } catch (Exception e) {
                    message.setText("Не вдалося отримати дані про погоду. Спробуйте ще раз. Помилка: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (messageText.equals("/mycity")) {
                if (userCities.containsKey(chatId)) {
                    String cityName = userCities.get(chatId);
                    try {
                        example.tgbot.WeatherService weatherService = new example.tgbot.WeatherService();
                        String weatherData = weatherService.getWeatherData(cityName);
                        WeatherParser weatherParser = new WeatherParser();
                        String weatherInfo = weatherParser.parseWeatherData(weatherData);
                        message.setText(weatherInfo);
                    } catch (Exception e) {
                        message.setText("Не вдалося отримати дані про погоду. Спробуйте ще раз. Помилка: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    waitingForMyCity = true;
                    message.setText("Введіть назву свого міста.");
                }
            } else if (waitingForMyCity) {
                waitingForMyCity = false;
                String cityName = messageText;
                userCities.put(chatId, cityName);
                message.setText("Ваше місто збережено. Використовуйте /mycity для отримання даних про погоду у вашому місті.");
            } else if (messageText.equals("/deletemycity")) {
                if (userCities.containsKey(chatId)) {
                    userCities.remove(chatId);
                    message.setText("Дані про ваше місто видалено.");
                } else {
                    message.setText("Ваше місто не знайдено.");
                }
            } else if (messageText.equals("/converttemp")) {
                waitingForTempConversion = true;
                message.setText("Напишіть температуру в градусах Цельсія:");
            } else if (waitingForTempConversion) {
                waitingForTempConversion = false;
                try {
                    double celsius = Double.parseDouble(messageText);
                    double fahrenheit = TemperatureConverter.celsiusToFahrenheit(celsius);
                    message.setText(String.format("Температура в градусах Фаренгейта: %.2f°F", fahrenheit));
                } catch (NumberFormatException e) {
                    message.setText("Невірний формат. Будь ласка, введіть числове значення.");
                }
            } else {
                message.setText("Невірна команда. Використовуйте /weather для початку.");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
