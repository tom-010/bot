package io.deniffel.bot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EchoTest {

    EchoBot bot;

    @Before
    public void setUp() throws Exception {
        bot = new EchoBot();
    }

    @Test
    public void sendingSimpleEcho_returnsTheSameMessageWithoutEcho() {
        assertEcho("hello", "echo hello");
        assertEcho("message", "echo message");
    }

    @Test
    public void leadingBlank_ignoresIt() {
        assertEcho("hello", " echo hello");
        assertEcho("hello", "   echo hello");
    }

    @Test
    public void multipleBlanksBeforeAndAfterEcho_ignoresIt() {
        assertEcho("hello", "echo  hello");
        assertEcho("hello", "echo       hello");
    }

    @Test
    public void null_emptyResponse() {
        assertFalse(bot.enter((Message) null).isPresent());
    }

    @Test
    public void doesNotStartWithEcho_emptyResponse() {
        assertFalse(bot.enter("some string without echo").isPresent());
    }

    @Test
    public void onlyEchoIsInMessage_emptyPresentMessageIsReturned() {
        Response response = bot.enter("echo");
        assertTrue(response.isPresent());
        assertEquals("", response.response());
    }

    private void assertEcho(String expectedResult, String message) {
        assertEquals(expectedResult, bot.enter(message).response());
    }
}
