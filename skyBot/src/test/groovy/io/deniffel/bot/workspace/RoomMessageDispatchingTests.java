package io.deniffel.bot.workspace;

import io.deniffel.bot.base.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomMessageDispatchingTests {

    private class Spy implements Listener {
        List<Message> receivedMessages = new LinkedList<>();

        @Override
        public void receive(Message message) {
            this.receivedMessages.add(message);
        }

        Message lastReceivedMessage() {
            return receivedMessages.get(receivedMessages.size()-1);
        }
    }

    private Spy listener;
    private Room room;

    @Before
    public void setUp() throws Exception {
        listener = new Spy();
        room = new Room("roomId");
    }

    @Test
    public void nullMessage_noDispatching() {
        room.addListener(listener);
        room.send((Message) null);
        assertEquals(0, listener.receivedMessages.size());
    }

    @Test
    public void validMessage_listenerReceivesMessage() {
        room.addListener(listener);
        room.send(message());
        assertEquals(1, listener.receivedMessages.size());
    }

    @Test
    public void multipleReceivers_everyoneGetsAMessage() {
        Spy listener1 = new Spy();
        Spy listener2 = new Spy();
        room.addListener(listener1);
        room.addListener(listener2);
        Message message = message();

        room.send(message);

        assertEquals(message, listener1.lastReceivedMessage());
        assertEquals(message, listener2.lastReceivedMessage());
        assertEquals(listener1.lastReceivedMessage(), listener2.lastReceivedMessage());
    }

    @Test
    public void multipleReceivers_everyoneGetsItsOwnCopy() {
        Spy listener1 = new Spy();
        Spy listener2 = new Spy();
        room.addListener(listener1);
        room.addListener(listener2);
        Message message = message();

        room.send(message);

        assertFalse(listener1.lastReceivedMessage() == listener2.lastReceivedMessage());
    }

    @Test
    public void messageSent_containsRoomId() {
        room.addListener(listener);
        room.send(message());
        assertEquals(room.getId(), listener.lastReceivedMessage().getRoomId());
    }

    private Message message() {
        return new Message("Message");
    }
}
