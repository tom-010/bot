package io.deniffel.bot;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SkyBot implements Bot {

    private List<Bot> subBot = new LinkedList<>();

    @Override
    public Response enter(String enteredString) {
        for(Bot bot : subBot) {
            Response response = bot.enter(enteredString);
            if(response != null && response.isPresent())
                return response;
        }
        return Response.notPresent();
    }

    public void register(Bot bot) {
        this.subBot.add(bot);
    }
}
