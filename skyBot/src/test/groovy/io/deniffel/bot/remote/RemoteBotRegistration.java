package io.deniffel.bot.remote;

import io.deniffel.bot.HttpMock;
import io.deniffel.bot.skyBot.RegistrationRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteBotRegistration {

    private RemoteBot bot;
    private HttpMock http;

    @Before
    public void setUp() throws Exception {
        http = new HttpMock();
        bot = new RemoteBot(http, "");
    }

    @Test
    public void firstRegistrationRequest_remoteBotIsRegistered() {
        RegistrationRequest registration = new RegistrationRequest("http://myUrl", ".*");
        bot.register(registration);
        assertTrue(bot.atLastOneRemoteBotIsRegistered());
    }

    @Test
    public void registrationWithNullAsUrl_doesNotAdd() {
        bot.register(new RegistrationRequest(null, ".*"));
        assertFalse(bot.atLastOneRemoteBotIsRegistered());
    }

    @Test
    public void registrationNull_doesNotAdd() {
        bot.register(null);
        assertFalse(bot.atLastOneRemoteBotIsRegistered());
    }

    @Test
    public void registrationWithEmptyUrl_doesNotAdd() {
        bot.register(new RegistrationRequest("", ".*"));
        assertFalse(bot.atLastOneRemoteBotIsRegistered());
    }
}
