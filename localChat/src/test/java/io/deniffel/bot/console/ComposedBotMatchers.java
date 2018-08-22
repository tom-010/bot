package io.deniffel.bot.console;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ComposedBotMatchers {

    class MatchersBot implements Bot {

        List<String> matchers = new LinkedList<>();

        @Override
        public Response enter(Message message) {
            return null;
        }

        public List<String> matchers() {
            return matchers;
        }
    }

    @Test
    public void init_matchersEmpty() {
        assertEquals(0, new ComposedBot().matchers().size());
    }

    @Test
    public void addingBotWithMatchers_matchersAreAddedToComposedBotMatchers() {
        ComposedBot bot = new ComposedBot();
        MatchersBot b1 = new MatchersBot();
        b1.matchers.add("one");
        b1.matchers.add("two");
        bot.put(b1);
        List<String> m = bot.matchers();

        assertEquals(2, m.size());
        assertTrue(m.contains("one"));
        assertTrue(m.contains("two"));
    }
}
