package io.deniffel.bot.acceptance;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import skyBot.EchoBot;
import io.deniffel.bot.base.Response;
import skyBot.SkyBot;

import static org.junit.Assert.*;

public class CommonSteps {

    SkyBot bot;
    Response lastResult;

    @Before
    public void setUp(){
        bot = new SkyBot();
        bot.register(new EchoBot());
        lastResult = null;
    }

    @When("^I enter \"([^\"]*)\"$")
    public void iEnter(String enteredString) throws Throwable {
        lastResult = bot.enter(enteredString);
    }

    @Then("^the bot returns \"([^\"]*)\"$")
    public void theBotReturns(String botsReturnValue) throws Throwable {
        assertEquals(botsReturnValue, lastResult.response());
    }
}