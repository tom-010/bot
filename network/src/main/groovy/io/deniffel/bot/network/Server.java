package io.deniffel.bot.network;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class Server {

    private List<Registration> registrations = new LinkedList<>();

    @PostMapping("/")
    public void greeting(@Valid @RequestBody Registration registration) {
        this.registrations.add(registration);
        System.out.println(registrations.size());
    }

    public static class Registration {
        public List<String> matchers = new LinkedList<>();
        public String url = "";
    }
}