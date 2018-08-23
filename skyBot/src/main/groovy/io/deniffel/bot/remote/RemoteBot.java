package io.deniffel.bot.remote;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.Configuration;

public class RemoteBot extends Slave implements Bot {

    public RemoteBot(Http http, String mastersUrl) {
        super(http); // TODO: delete this
        this.http = http;
    }

    public RemoteBot(Http http, Configuration configuration) {
        super(http);
        this.http = http;
    }

    @Override
    public Response enter(Message message) {
        return sendMessageToMaster(message);
    }
}
