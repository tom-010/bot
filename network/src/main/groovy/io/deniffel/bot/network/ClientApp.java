package io.deniffel.bot.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApp {

    public static void main(String[] args) throws Exception {
        Server.Registration registration = new Server.Registration();
        registration.url = "http://localhost:3001";
        registration.matchers.add(".*");

        Http.post("http://localhost:3000/", registration);

        // SpringApplication.run(ClientApp.class, args);
    }
}
