package io.deniffel.bot.acceptance;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.deniffel.bot.Response;
import io.deniffel.bot.SkyBot;

import static org.junit.Assert.*;

public class CommonSteps {

    SkyBot bot = new SkyBot();

    Response lastResult = null;

    @When("^I enter \"([^\"]*)\"$")
    public void iEnter(String enteredString) throws Throwable {
        lastResult = bot.enter(enteredString);
    }

    @Then("^the bot returns \"([^\"]*)\"$")
    public void theBotReturns(String botsReturnValue) throws Throwable {
        assertEquals(botsReturnValue, lastResult.content());
    }
}