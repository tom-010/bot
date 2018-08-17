package io.deniffel.bot.skyBot;

public interface Http {
    Response sendMessage(Message message, String url);
}
