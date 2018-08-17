package io.deniffel.bot;

public interface Bot {
    default Response enter(String enteredString) {
        return enter(new Message(enteredString));
    }

    Response enter(Message message);
}
