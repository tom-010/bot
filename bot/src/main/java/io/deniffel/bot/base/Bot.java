package io.deniffel.bot.base;


public interface Bot {
    default Response enter(String enteredString) {
        return enter(new Message(enteredString));
    }
    Response enter(Message message);
    default void receive(Message message) {
        enter(message);
    }
}
