package fr.diginamic.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("web")
public class HelloApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "web");
        SpringApplication.run(HelloApplication.class, args);
    }
}

