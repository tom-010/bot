package io.deniffel.bot.base;

import java.util.LinkedList;
import java.util.List;

public interface Bot {

    default Response enter(String enteredString) {
        return enter(new Message(enteredString));
    }

    Response enter(Message message);
    default List<String> matchers() {
        return new LinkedList<>();
    }

    default void receive(Message message) {
        enter(message);
    }
}
