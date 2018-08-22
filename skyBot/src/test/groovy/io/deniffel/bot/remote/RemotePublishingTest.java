package io.deniffel.bot.remote;

import io.deniffel.bot.HttpMock;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.skyBot.Configuration;
import io.deniffel.bot.workspace.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RemotePublishingTest {

    private HttpMock http;
    private Room room;
    private RemotePublisher publisher;
    Configuration configuration;

    @Before
    public void setUp() throws Exception {
        http = new HttpMock();
        room = new Room("1");
        publisher = new RemotePublisher(http);
        room.addListener(publisher);
    }

    @Test
    public void messageSent_triggersHttp() {
        room.send(new Message("message"));
        assertTrue(http.postWasCalled);
    }

    @Test
    public void messageSent_toMastersUrl() {
        room.send(new Message("message"));
    }


}
