package io.deniffel.bot.remote;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.RegistrationRequest;
import io.deniffel.bot.skyBot.RegistrationResponse;

public interface Http {
    Response sendMessage(Message message, String url);
    RegistrationResponse sendRegistrationRequest(RegistrationRequest request, String url);
    void post(String url, Object content);

}
