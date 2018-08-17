package io.deniffel.bot;

public class EchoBot implements Bot {

    private String keyword = "echo";

    @Override
    public Response enter(Message enteredString) {
        if(enteredString == null)
            return Response.notPresent();

        String input = enteredString.stingContent().trim();

        if(!input.startsWith(keyword))
            return Response.notPresent();

        String answer= input.substring(keyword.length()).trim();
        return Response.of(answer);
    }
}
