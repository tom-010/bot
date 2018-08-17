package io.deniffel.bot;

public class Message {

    private String message;

    public Message(String enteredString) {
        message = enteredString;
    }

    public String stingContent() {
        return message;
    }

    public boolean valid() {
        return message != null && !message.trim().isEmpty();
    }
}
