package io.deniffel.bot.base;

import io.deniffel.bot.base.Message;

@FunctionalInterface
public interface Listener {
    void receive(Message message);
}
