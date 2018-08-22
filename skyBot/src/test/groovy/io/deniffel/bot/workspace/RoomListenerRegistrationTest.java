package io.deniffel.bot.workspace;

import io.deniffel.bot.base.Listener;
import io.deniffel.bot.base.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class RoomListenerRegistrationTest {

    private class ListenerMock implements Listener {
        private String id;
        public ListenerMock(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListenerMock that = (ListenerMock) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public void receive(Message message) {

        }
    }

    private Room room;

    @Before
    public void setUp() throws Exception {
        room = new Room("roomId");
    }

    @Test
    public void null_noListenerIsRegistered() {
        room.addListener(null);
        assertEquals(0, room.listenersCount());
    }

    @Test
    public void listenersRegisters_countIncreasesByOne() {
        room.addListener(new ListenerMock("id"));
        assertEquals(1, room.listenersCount());
    }

    @Test
    public void registerTwice_onlyOneRegistrationIsUsed() {
        room.addListener(new ListenerMock("id1")); // Note: equality through equals not
        room.addListener(new ListenerMock("id1")); //       through == is required here
        assertEquals(1, room.listenersCount());
    }
}
