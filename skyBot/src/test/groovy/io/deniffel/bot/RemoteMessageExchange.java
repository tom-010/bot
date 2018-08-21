package io.deniffel.bot;

import io.deniffel.bot.base.Message;
import skyBot.RegistrationRequest;
import skyBot.RemoteBot;
import io.deniffel.bot.base.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteMessageExchange {

    private RemoteBot bot;
    private RemoteBot botWithoutRegistrations;
    private HttpMock http;


    @Before
    public void setUp() throws Exception {
        http = new HttpMock();
        botWithoutRegistrations = new RemoteBot(http);
        bot = new RemoteBot(http);
        bot.register(new RegistrationRequest("http://myUrl", ".*"));
    }

    @Test
    public void message_causesAHttpPostRequest() {
        bot.enter("valid message");
        assertTrue(http.wasCalled);
    }

    @Test
    public void nullMessage_causesNoHttpRequestIfMessageIsNotValid() {
        bot.enter((Message) null);
        assertFalse(http.wasCalled);
    }

    @Test
    public void inValidMessage_causesNoHttpRequestIfMessageIsNotValid() {
        Message toSend = new Message("");
        assertFalse(toSend.valid());

        bot.enter(toSend);
        assertFalse(http.wasCalled);
    }

    @Test
    public void null_responseNotPresent() {
        assertFalse(bot.enter((Message) null).isPresent());
    }

    @Test
    public void invalidMessage_responseNotPresent() {
        Message invalidMessage = new Message("");
        assertFalse(invalidMessage.valid());
        assertFalse(bot.enter(invalidMessage).isPresent());
    }

    @Test
    public void noOtherBotIsRegistered_doesNotCall() {
        Message validMessage = new Message("some content");
        assertTrue(validMessage.valid());

        botWithoutRegistrations.enter(validMessage);
        assertFalse(http.wasCalled);
    }

    @Test
    public void message_sendsToUrlOfRegisteredRemoteBot() {
        RemoteBot bot = botWithoutRegistrations;
        bot.register(new RegistrationRequest("http://bots-endpoint/", ".*"));
        bot.enter("some message");
        assertEquals("http://bots-endpoint/", http.lastUrlUsed);
    }

    @Test
    public void message_toBotThatMatches() {
        RemoteBot bot = botWithoutRegistrations;
        RegistrationRequest otherBot = new RegistrationRequest("http://bot-with-regex", "bot .*");
        bot.register(otherBot);
        bot.enter("bot message");
        assertEquals("http://bot-with-regex", http.lastUrlUsed);
    }

    @Test
    public void botDoesNotMatch_noMessage() {
        RemoteBot bot = botWithoutRegistrations;
        RegistrationRequest otherBot = new RegistrationRequest("http://bot-with-regex", "bot .*");
        bot.register(otherBot);

        bot.enter("some message, that does not match");
        assertFalse(http.wasCalled);
    }

    @Test
    public void multipleBotsMatch_onlyOneMessageIsSent() {
        RemoteBot bot = botWithoutRegistrations;
        bot.register(new RegistrationRequest("http://b1", "bot .*"));
        bot.register(new RegistrationRequest("http://b2", "bot .*"));

        bot.enter("bot some message");

        assertEquals(1, http.totalRequests);
        assertEquals("http://b1", http.lastUrlUsed);
    }

    @Test
    public void noBotMatchedMessage_resultNotPresent() {
        RemoteBot bot = botWithoutRegistrations;
        bot.register(new RegistrationRequest("http://b1", "bot1 .*"));
        bot.register(new RegistrationRequest("http://b2", "bot2 .*"));

        Response r = bot.enter("this message does not match to bot1 or bot2");

        assertFalse(r.isPresent());
    }

    @Test
    public void nullMatcher_doesNotMatch() {
        RemoteBot bot = botWithoutRegistrations;
        bot.register(new RegistrationRequest("http://b1", null));

        Response r = bot.enter("message");

        assertFalse(r.isPresent());
        assertFalse(http.wasCalled);
    }

    /*
    Git hooks
    Regex
     */
}



































