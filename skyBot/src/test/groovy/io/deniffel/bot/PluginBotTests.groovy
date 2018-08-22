package io.deniffel.bot

import io.deniffel.bot.base.Message
import io.deniffel.bot.base.Response
import io.deniffel.bot.skyBot.PluginManager
import org.junit.Before
import org.junit.Test
import io.deniffel.bot.skyBot.PluginBot

import static org.junit.Assert.*

class PluginBotTests {

    private PluginBot uut
    private PluginManagerMock pluginManger

    @Before
    void setUp() throws Exception {
        pluginManger = new PluginManagerMock()
        uut = new PluginBot(pluginManger)
    }

    @Test
    void nullMessage_responseNotPresent() {
        assertFalse(uut.enter((Message) null).isPresent())
    }

    @Test
    void emptyMessage_responseNotPresent() {
        assertFalse(uut.enter("").isPresent())
    }

    @Test
    void pluginRegisteredThatExceptsMessage_pluginsAnswerIsSent() {
        pluginManger.acceptsNextMessage = true
        pluginManger.nextAnswer = "answer"

        Response response = uut.enter("message")

        assertTrue(response.isPresent())
        assertEquals("answer", response.asString())
    }

    @Test
    void noPluginAcceptsMessage_responseNotPresent() {
        pluginManger.acceptsNextMessage = false
        pluginManger.nextAnswer = "answer"

        Response response = uut.enter("message")

        assertFalse(response.isPresent())
    }

    @Test
    void resultListOfAMatchingPluginNull_responseNotPresent() {
        pluginManger.useNextResultLists = true
        pluginManger.nextResultList = null

        Response response = uut.enter("message")

        assertFalse(response.isPresent())
    }

    @Test
    void resultListOfAMatchingPluginEmpty_responseNotPresent() {
        pluginManger.useNextResultLists = true
        pluginManger.nextResultList = []

        Response response = uut.enter("message")

        assertFalse(response.isPresent())
    }

    @Test
    void constructionWithOnlyBasePath_works() {
        new PluginBot(".")
        // no Exception
    }

    class PluginManagerMock extends PluginManager {

        int answerWasCalledTimes = 0
        String nextAnswer = null
        Map<String, String> nextContext = [:]
        String lastMessage = null
        Map<String, String> lastContext = null
        boolean acceptsNextMessage = true

        boolean useNextResultLists = false
        def nextResultList = null

        PluginManagerMock() {
            super(".")
        }

        def answer(String message, Map<String, String> ctx = [:]) {
            answerWasCalledTimes++

            lastMessage = message
            lastContext = ctx

            if(useNextResultLists)
                return nextResultList

            if(acceptsNextMessage)
                return [nextAnswer, nextContext]
            else
                return [null, ctx]
        }
    }
}
