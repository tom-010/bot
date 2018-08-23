package io.deniffel.bot.base;

import java.util.Objects;

public class Message implements Cloneable {

    private String message;
    private String roomId;

    public Message(String enteredString) {
        message = enteredString;
    }

    public String stingContent() {
        return message;
    }

    public boolean valid() {
        return message != null && !message.trim().isEmpty();
    }

    public Message clone() {
        try {
            return (Message) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
