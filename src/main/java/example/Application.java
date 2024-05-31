package example;
import example.tgbot.MyTelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new Thread(() -> {
            SpringApplication.run(Application.class, args);
        }).start();

        new Thread(() -> {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new MyTelegramBot());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }).start();

    }
}