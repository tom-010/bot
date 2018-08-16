package io.deniffel.bot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubBotTests {

    private SkyBot bot;
    private MockBot subBot;

    private static class MockBot implements Bot {

        String lastEnteredString = null;
        boolean wasCalled = false;
        Response nextResult = null;

        @Override
        public Response enter(String enteredString) {
            lastEnteredString = enteredString;
            wasCalled = true;
            return nextResult;
        }
    }

    @Before
    public void setUp() {
        subBot = new MockBot();
        bot = new SkyBot();
        bot.register(subBot);
    }

    @Test
    public void message_delegatesMessageToSubBot() {
        bot.enter("some string, that could possibly be interpreted by subbot");
        assertTrue(subBot.wasCalled);
    }

    @Test
    public void subBotResultsInResponse_responseFromSubBotIsActuallyTaken() {
        Response response = Response.of("");
        subBot.nextResult = response;
        assertEquals(response, bot.enter("some string, subBots knows everything, therefore sends a response in every case"));
    }

    @Test
    public void subBotDoesNotRespond_subBotsResponseIsDiscarded() {
        Response response = Response.notPresent();
        subBot.nextResult = response;
        assertNotEquals(response, bot.enter("don't know this message"));
    }

    @Test
    public void onlySecondSubBotsKnowsTheAnswer_secondsBotResponse() {
        MockBot b1 = new MockBot();
        MockBot b2 = new MockBot();
        bot.register(b1);
        bot.register(b2);

        b1.nextResult = Response.notPresent();
        Response response = Response.of("");
        b2.nextResult = response;

        assertEquals(response, bot.enter("string"));
    }

    @Test
    public void onlyFirstSubBotsKnowsTheAnswer_firstsBotResponse() {
        MockBot b1 = new MockBot();
        MockBot b2 = new MockBot();
        bot.register(b1);
        bot.register(b2);


        Response response = Response.of("");
        b1.nextResult = response;

        b2.nextResult = Response.notPresent();

        assertEquals(response, bot.enter("string"));
    }
}
