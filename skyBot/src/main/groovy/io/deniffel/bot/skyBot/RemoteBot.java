package io.deniffel.bot.skyBot;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

import java.util.LinkedList;
import java.util.List;

public class RemoteBot implements Bot {

    private Http http;
    private List<RegisteredBot> registeredBots = new LinkedList<>();

    public RemoteBot(Http http) {
        this.http = http;
    }

    @Override
    public Response enter(Message message) {
        if(message == null || !message.valid() || !atLastOneRemoteBotIsRegistered())
            return Response.notPresent();

        for(RegisteredBot bot : registeredBots) {
            if(bot.matches(message))
                return http.sendMessage(message, registeredBots.get(0).url);
        }

        return Response.notPresent();
    }

    public boolean atLastOneRemoteBotIsRegistered() {
        return !registeredBots.isEmpty();
    }

    public void register(RegistrationRequest registration) {
        if(!registrationAllowed(registration))
            return;

        registeredBots.add(new RegisteredBot(registration));
    }

    private boolean registrationAllowed(RegistrationRequest request) {
        return request != null && request.getMyUrl() != null && !request.getMyUrl().isEmpty();
    }

    private class RegisteredBot {
        String url;
        String matcher;

        RegisteredBot(RegistrationRequest request) {
            this.url = request.getMyUrl();
            this.matcher = request.getMatcher();
        }

        boolean matches(Message message) {
            if(matcher == null)
                return false;

            return message.stingContent().matches(matcher);
        }
    }
}
