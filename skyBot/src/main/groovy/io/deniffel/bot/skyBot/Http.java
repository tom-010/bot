package io.deniffel.bot.skyBot;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

public interface Http {
    Response sendMessage(Message message, String url);
    RegistrationResponse sendRegistrationRequest(RegistrationRequest request, String url);
}
