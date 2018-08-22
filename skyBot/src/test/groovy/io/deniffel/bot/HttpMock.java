package io.deniffel.bot;

import io.deniffel.bot.remote.Http;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.RegistrationRequest;
import io.deniffel.bot.skyBot.RegistrationResponse;

public class HttpMock implements Http {
    public boolean wasCalled = false;
    public String lastUrlUsed = null;
    public int totalRequests = 0;
    public boolean postWasCalled = false;

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

    @Override
    public void post(String url, Object content) {
        wasCalled = true;
        postWasCalled = true;
    }

}
