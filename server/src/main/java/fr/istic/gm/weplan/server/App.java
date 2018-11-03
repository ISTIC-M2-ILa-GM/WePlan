package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.infra.client.weather.impl.WeatherClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The application
 */
@SpringBootApplication
@EnableScheduling
public class App {

    private WeatherClientImpl weatherClient;

    /**
     * The main application.
     *
     * @param args the args of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Scheduled(cron = "0 * * * * *")
    public void doTheHarlemShake(){
        System.out.println("Hey there ! Time to go to sleep now !");
    }
}
