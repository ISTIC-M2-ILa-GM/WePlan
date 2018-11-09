package fr.istic.gm.weplan.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The application
 */
@SpringBootApplication
@EnableScheduling
public class App {
    /**
     * The main application.
     *
     * @param args the args of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
