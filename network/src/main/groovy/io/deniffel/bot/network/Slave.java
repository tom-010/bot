package io.deniffel.bot.network;

import io.deniffel.bot.base.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@RestController
public class Slave {

    List<Message> received = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        Master.Registration registration = new Master.Registration();
        registration.url = "http://localhost:3001/message";
        registration.matchers.add(".*");

        Http.post("http://localhost:3000/", registration);

        SpringApplication.run(Slave.class, args);
    }

    @PostMapping("/message")
    public void greeting(@Valid @RequestBody Message message) {
        System.out.println("received " + message.stingContent());
    }
}
