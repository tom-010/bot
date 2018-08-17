package io.deniffel.bot;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.SkyBot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrangeInputs {

    SkyBot bot;

    @Before
    public void setUp() throws Exception {
        bot = new SkyBot();
    }

    @Test
    public void null_noResponse() {
        Response response = bot.enter((Message) null);
        assertFalse(response.isPresent());
    }

    @Test
    public void emptyString_noResponse() {
        Response response =  bot.enter("");
        assertFalse(response.isPresent());
    }
}
