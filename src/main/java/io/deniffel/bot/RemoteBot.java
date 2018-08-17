package io.deniffel.bot;

public class RemoteBot implements Bot {

    Http http;

    public RemoteBot(Http http) {
        this.http = http;
    }

    @Override
    public Response enter(Message message) {
        if(message == null || !message.valid())
            return Response.notPresent();
        return http.sendMessage(message, "");
    }

    public boolean atLastOneRemoteBotIsRegistered() {
        return false;
    }
}
