package io.deniffel.bot.base;

import io.deniffel.bot.skyBot.Message;
import io.deniffel.bot.skyBot.Response;

public interface Bot {
    default Response enter(String enteredString) {
        return enter(new Message(enteredString));
    }

    Response enter(Message message);
}
