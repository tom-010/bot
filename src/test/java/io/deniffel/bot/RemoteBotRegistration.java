package io.deniffel.bot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteBotRegistration {

    RemoteBot bot;
    HttpMock http;

    @Before
    public void setUp() throws Exception {
        http = new HttpMock();
        bot = new RemoteBot(http);
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
