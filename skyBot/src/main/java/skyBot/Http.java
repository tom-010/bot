package skyBot;

import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

public interface Http {
    Response sendMessage(Message message, String url);
}
