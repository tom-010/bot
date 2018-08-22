package io.deniffel.bot.network;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Slave {

    public static void main(String[] args) throws Exception {
        Master.Registration registration = new Master.Registration();
        registration.url = "http://localhost:3001";
        registration.matchers.add(".*");

        Http.post("http://localhost:3000/", registration);

        // SpringApplication.run(ClientApp.class, args);
    }
}
