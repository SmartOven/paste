package ru.panteleevya.paste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasteApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PasteApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
