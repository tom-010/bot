package io.deniffel.bot;

public class RegistrationRequest {

    private String myUrl;
    private String matcher;

    public RegistrationRequest(String myUrl, String matcher) {
        this.myUrl = myUrl;
        this.matcher = matcher;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public String getMatcher() {
        return matcher;
    }
}
