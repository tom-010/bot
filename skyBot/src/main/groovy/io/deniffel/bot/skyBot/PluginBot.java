package io.deniffel.bot.skyBot;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

import java.util.ArrayList;

public class PluginBot implements Bot {

    private PluginManager pluginManager;

    public PluginBot(String pluginBasePath) {
        this.pluginManager = PluginManager.build(pluginBasePath);
    }

    public PluginBot(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public Response enter(Message message) {
        if(message == null || !message.valid())
            return Response.notPresent();

        Object result = pluginManager.answer(message.stingContent());
        if(result == null)
            return Response.notPresent();

        ArrayList resultList = (ArrayList) result;
        if(resultList.size() == 0)
            return Response.notPresent();

        String answer = (String) resultList.get(0);

        if(answer == null)
            return Response.notPresent();

        return Response.of(answer);
    }
}
