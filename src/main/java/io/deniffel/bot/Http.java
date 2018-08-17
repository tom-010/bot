package io.deniffel.bot;

public interface Http {
    Response sendMessage(Message message, String url);
}
