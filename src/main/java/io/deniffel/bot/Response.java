package io.deniffel.bot;

public class Response {
    private boolean present = false;

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String content() {
        return null;
    }


    public static Response of(String later) {
        Response r = new Response();
        r.setPresent(true);
        return r;
    }

    public static Response notPresent() {
        Response r = new Response();
        r.setPresent(false);
        return r;
    }
}
