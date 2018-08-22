package io.deniffel.bot.skyBot;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

import java.util.LinkedList;
import java.util.List;

public class RemoteBot implements Bot, Master {

    private Http http;
    private List<RegisteredBot> registeredBots = new LinkedList<>();
    private Configuration configuration;

    public RemoteBot(Http http) {
        this.http = http;
    }

    public RemoteBot(Http http, Configuration configuration) {
        this.http = http;
        this.configuration = configuration;
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

    @Override
    public void register(RegistrationRequest registration) {
        if(!registrationAllowed(registration))
            return;

        registeredBots.add(new RegisteredBot(registration));
    }

    private boolean registrationAllowed(RegistrationRequest request) {
        return request != null && request.getMyUrl() != null && !request.getMyUrl().isEmpty();
    }

    public boolean atLastOneRemoteBotIsRegistered() {
        return !registeredBots.isEmpty();
    }

    public void sendRegistrationToMaster() {
        http.sendRegistrationRequest(new RegistrationRequest("", ""), configuration.urls.master);
    }

    private class RegisteredBot {
        String url;
        List<String> matchers = new LinkedList<>();

        RegisteredBot(RegistrationRequest request) {
            this.url = request.getMyUrl();
            this.matchers.add( request.getMatcher() );
        }

        boolean matches(Message message) {
            if(matchers == null)
                return false;

            for(String matcher : matchers)
                if(matcher != null && message.stingContent().matches(matcher))
                    return true;

            return false;
        }
    }
}
