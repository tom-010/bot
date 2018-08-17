package io.deniffel.bot.skyBot;

public class Response {
    private boolean present = false;
    private String content;

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public static Response of(String messageContent) {
        Response r = new Response();
        r.setPresent(true);
        r.content = messageContent;
        return r;
    }

    public static Response notPresent() {
        Response r = new Response();
        r.setPresent(false);
        return r;
    }

    public String response() {
        return content;
    }
}
