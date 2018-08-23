package io.deniffel.bot.base;

@FunctionalInterface
public interface Listener {
    void receive(Message message);
}
