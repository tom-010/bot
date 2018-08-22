package io.deniffel.bot.workspace;

import io.deniffel.bot.base.Listener;
import io.deniffel.bot.base.Message;

import java.util.*;

public class Room {

    private final String id;
    private Set<Listener> listeners = new HashSet<>();
    private List<Message> messages = new LinkedList<>();

    public Room(String id) {
        this.id = id;
    }

    public void addListener(Listener listener) {
        if(listener != null)
            listeners.add(listener);
    }

    public String getId() {
        return id;
    }

    public int listenersCount() {
        return listeners.size();
    }

    public void send(Message message) {
        send(message, this);
    }

    public void send(Message message, Object sender) {
        if(message == null)
            return;

        if(messages.contains(message))
            return;
        messages.add(message);

        message.setRoomId(this.id);

        for(Listener l : listeners) {
            l.receive(message.clone());
        }

    }
}
