package io.deniffel.bot.network;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import io.deniffel.bot.base.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SpringBootApplication
@RestController
public class Master {

    private static List<Registration> registrations = new LinkedList<>();

    public static void main(String[] args) {

        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {

                for(Registration registration : registrations) {
                    try {
                        Http.post(registration.url, new Message("message from master"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, 0, (1000 * 1));

        SpringApplication.run(Master.class, args);
    }

    @PostMapping("/")
    public void greeting(@Valid @RequestBody Registration registration) {
        this.registrations.add(registration);
        System.out.println(registrations.size());
    }

    public static Master build() {
        return new Master();
    }


    public static class Registration {
        public List<String> matchers = new LinkedList<>();
        public String url = "";
    }
}