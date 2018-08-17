package io.deniffel.bot;

import io.deniffel.bot.skyBot.Http;
import io.deniffel.bot.skyBot.Message;
import io.deniffel.bot.skyBot.Response;

class HttpMock implements Http {
    boolean wasCalled = false;
    String lastUrlUsed = null;
    int totalRequests = 0;

    @Override
    public Response sendMessage(Message message, String url) {
        wasCalled = true;
        lastUrlUsed = url;
        totalRequests++;
        return null;
    }
}
