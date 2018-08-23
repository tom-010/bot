package io.deniffel.bot.chat;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ComposedBotAddingTest {

    class DummyBot implements Bot {

        @Override
        public Response enter(Message message) {
            return null;
        }
    }

    class DummyBotWithCustomEquals implements Bot {

        private String id;
        String someInternalInfos;

        public DummyBotWithCustomEquals(String id) {
            this.id = id;
        }

        @Override
        public Response enter(Message message) {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DummyBotWithCustomEquals that = (DummyBotWithCustomEquals) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id);
        }
    }

    @Test
    public void noBotRegistered_answerIsNotPresent() {
        assertFalse(new ComposedBot().enter(new Message("someMessage")).isPresent());
    }

    @Test
    public void registeringFirstBots_increasesRegisteredBots() {
        ComposedBot bot = new ComposedBot();
        DummyBot insideBot = new DummyBot();
        bot.put(insideBot);
        assertEquals(1, bot.getBots().size());
    }

    @Test
    public void addSameBotTwice_onlyOneIsAdded() {
        ComposedBot bot = new ComposedBot();
        DummyBot insideBot = new DummyBot();
        bot.put(insideBot);
        bot.put(insideBot);
        assertEquals(1, bot.getBots().size());
    }

    @Test
    public void addDifferentBotsTwice_bothAdded() {
        ComposedBot bot = new ComposedBot();
        bot.put(new DummyBot());
        bot.put(new DummyBot());
        assertEquals(2, bot.getBots().size());
    }

    @Test
    public void botHasCustomEqualsAddedTwice_onyOneAdded() {
        ComposedBot bot = new ComposedBot();
        bot.put(new DummyBotWithCustomEquals("1"));
        bot.put(new DummyBotWithCustomEquals("1"));
        assertEquals(1, bot.getBots().size());
    }

    @Test
    public void updatedBotAdded_botInCollectionGetsUpdated() {
        ComposedBot bot = new ComposedBot();
        DummyBotWithCustomEquals b1 = new DummyBotWithCustomEquals("1");
        b1.someInternalInfos = "old_info";
        bot.put(b1);

        DummyBotWithCustomEquals b2 = new DummyBotWithCustomEquals("1"); // same ID
        b1.someInternalInfos = "new_info";
        bot.put(b2);

        assertEquals(1, bot.getBots().size());
        assertEquals("new_info",
                ((DummyBotWithCustomEquals) bot.getBots().stream().findFirst().get()).someInternalInfos); // In a set, the first and only element is syntactically hard to get...
    }
}
