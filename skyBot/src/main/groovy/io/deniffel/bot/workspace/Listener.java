package io.deniffel.bot.workspace;

import io.deniffel.bot.base.Message;

@FunctionalInterface
public interface Listener {
    void receive(Message message);
}
