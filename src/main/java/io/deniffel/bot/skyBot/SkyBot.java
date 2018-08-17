package io.deniffel.bot.skyBot;

import io.deniffel.bot.base.Bot;

import java.util.LinkedList;
import java.util.List;

public class SkyBot implements Bot {

    private List<Bot> subBot = new LinkedList<>();

    @Override
    public Response enter(Message enteredString) {
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
