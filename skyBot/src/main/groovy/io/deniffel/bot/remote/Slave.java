package io.deniffel.bot.remote;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.RegistrationRequest;

import java.util.LinkedList;
import java.util.List;

public class Slave {

    protected Http http;
    protected List<RegisteredBot> masters = new LinkedList<>();

    public Slave(Http http) {
        this.http = http;
    }

    public void register(RegistrationRequest registration) {
        if(!registrationAllowed(registration))
            return;

        masters.add(new RegisteredBot(registration));
    }

    private boolean registrationAllowed(RegistrationRequest request) {
        return request != null && request.getMyUrl() != null && !request.getMyUrl().isEmpty();
    }

    public boolean atLastOneRemoteBotIsRegistered() {
        return !masters.isEmpty();
    }

    public void registerMySelfOnMasters() {
        for(RegisteredBot b : masters)
            http.sendRegistrationRequest(new RegistrationRequest("", ""), b.url);
    }

    protected Response sendMessageToMaster(Message message) {
        if(message == null || !message.valid() || !atLastOneRemoteBotIsRegistered())
            return Response.notPresent();

        for(RegisteredBot bot : masters) {
            if(bot.matches(message))
                return http.sendMessage(message, bot.url);
        }

        return Response.notPresent();
    }

    protected class RegisteredBot {
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
