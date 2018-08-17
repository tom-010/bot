package io.deniffel.bot;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteMessageExchange {

    RemoteBot bot;
    HttpMock http;


    class HttpMock implements Http {
        boolean wasCalled = false;
        @Override public Response sendMessage(Message message, String url) {
            wasCalled = true;
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        http = new HttpMock();
        bot = new RemoteBot(http);
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
    @Ignore
    public void noOtherBotIsRegistered_doesNotCall() {
        assertFalse(bot.atLastOneRemoteBotIsRegistered());
        Message validMessage = new Message("some content");
        assertTrue(validMessage.valid());
        bot.enter(validMessage);
        assertFalse(http.wasCalled);
    }

    /*
    Doesnt call, if no other remote is registered
     */
}
