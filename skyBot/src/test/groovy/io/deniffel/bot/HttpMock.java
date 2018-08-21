package io.deniffel.bot;

import skyBot.Http;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

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
