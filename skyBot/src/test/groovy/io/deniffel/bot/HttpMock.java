package io.deniffel.bot;

import io.deniffel.bot.skyBot.Http;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.RegistrationRequest;
import io.deniffel.bot.skyBot.RegistrationResponse;

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

    @Override
    public RegistrationResponse sendRegistrationRequest(RegistrationRequest request, String url) {
        this.lastUrlUsed = url;
        return null;
    }

}
