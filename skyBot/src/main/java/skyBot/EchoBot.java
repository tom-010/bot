package skyBot;
import io.deniffel.bot.base.Bot;
import io.deniffel.bot.base.Message;
import io.deniffel.bot.base.Response;

public class EchoBot implements Bot {

    @Override
    public Response enter(Message enteredString) {
        if(enteredString == null)
            return Response.notPresent();

        String input = enteredString.stingContent().trim();

        if(!input.startsWith("echo"))
            return Response.notPresent();

        String answer= input.substring("echo".length()).trim();
        return Response.of(answer);
    }
}
