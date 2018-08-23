package io.deniffel.bot.chat.acceptance;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.deniffel.bot.chat.acceptance.FSMock;
import io.deniffel.bot.chat.LocalChat;
import io.deniffel.bot.skyBot.PluginManager;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicFeature {

    private LocalChat chat;
    private PluginManager pluginManager;
    private FSMock fs;
    private List<String> answers = new LinkedList<>();
    private String lastAnswer = null;

    @Before
    public void setUp() {
        fs = new FSMock();
        pluginManager = new PluginManager("/", fs);
        lastAnswer = null;
        answers = new LinkedList<>();
        chat = new LocalChat(pluginManager);
    }

    @When("^I enter \"([^\"]*)\"$")
    public void iEnter(String userInput) {
        lastAnswer = chat.handleInput(userInput);
        if(lastAnswer != null)
            answers.add(lastAnswer);
    }

    @When("^I create a echo script$")
    public void iCreateAEchoScript()
    {
        int scriptsBefore = pluginManager.getScripts().size();
        fs.nextFilesResult.add(Paths.get("echo.groovy"));

        fs.nextFileReadResult =
                "def activatorRegexes() { return [\"echo .*\"]; }\n" +
                "def answer(String incomingMessage, Map<String, Object> context = [:]) { return [incomingMessage.substring(5), context] }";
        pluginManager.refresh();

        assertEquals(scriptsBefore+1,  pluginManager.getScripts().size());
    }

    @Then("^the bot returns \"([^\"]*)\"$")
    public void theBotReturns(String expectedResult) throws Throwable {
        assertEquals(expectedResult, lastAnswer);
    }

    @Then("^no answer comes from the chat$")
    public void noAnswerComesFromTheChat() throws Throwable {
        assertNull(lastAnswer);
    }

    @Then("^the bot responded only once$")
    public void theBotRespondedOnlyOnce() throws Throwable {
        assertEquals(1, answers.size());
    }
}
