package io.deniffel.bot.remote;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Listener;

public class RemotePublisher implements Listener {

    private Http http;

    public RemotePublisher(Http http) {
        this.http = http;
    }

    @Override
    public void receive(Message message) {
        http.post("", message);
    }
}
