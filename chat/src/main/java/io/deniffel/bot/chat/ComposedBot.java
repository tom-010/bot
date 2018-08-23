package io.deniffel.bot.chat;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ComposedBot implements Bot {

    private Set<Bot> bots = new HashSet<>();


    @Override
    public Response enter(Message message) {
        return Response.notPresent();
    }

    @Override
    public List<String> matchers() {
        List<String> result = new LinkedList<>();
        for(Bot b : bots)
            result.addAll(b.matchers());
        return result;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public void put(Bot insideBot) {
        bots.add(insideBot);
    }
}
