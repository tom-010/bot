package io.deniffel.bot.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deniffel.bot.base.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static org.junit.Assert.*;

public class NetworkingTests {

    // Ziel: Bot1 ---Message--> Bot2
    // 1. Bot1 meldet sich in channel an
    // 2. Bot2 meldet sich in channel an
    // 3. Bot1 sendet nachricht in channel
    // 4. Bot2 erhält nachricht
    // 5. Bot2 antwortet
    // 6. Bot1 erhält die Antwort

    static class Context {
        public static Http http;
    }

    static class HttpResponse {
        private int statusCode = 200;

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public static HttpResponse withCode(int statusCode) {
            HttpResponse response = new HttpResponse();
            response.setStatusCode(statusCode);
            return response;
        }
    }

    interface Http {
        HttpResponse post(String channelUrl, String content);
    }

    class HttpMock implements Http {

        public String lastUrl;
        public String lastContentSent;
        public boolean isAvailable = true;
        public int requestCount = 0;

        @Override
        public HttpResponse post(String url, String content) {
            requestCount++;
            lastUrl = url;
            lastContentSent = content;
            if(isAvailable)
                return HttpResponse.withCode(200);
            else
                return HttpResponse.withCode(503);
        }
    }

    class RemoteBot {


        private String respondToUrl;

        public RemoteBot(String respondToUrl) {
            this.respondToUrl = respondToUrl;
        }

        public void registerForChannel(String channelUrl) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String val = mapper.writeValueAsString(new RegistrationRequest(respondToUrl));

                HttpResponse response;
                int remainingAttempts = 3;
                do {
                    response = Context.http.post(channelUrl, val);
                } while(response.getStatusCode() != 200 && (--remainingAttempts) > 0);

                if(response.getStatusCode() != 200)
                    throw new RegistrationFailedException();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
    }

    private HttpMock http;
    private RemoteBot bot;
    private String channelUrl = "http://master-url/channel1";
    private String respondToUrl = "http://respond-to.com:8080";

    @Test
    public void botRegistration_botSendsMessageOverHttp() {
        bot.registerForChannel(channelUrl);
        assertEquals(channelUrl, http.lastUrl);
    }

    static class RegistrationRequest {
        public String respondTo;

        public RegistrationRequest() {

        }
        public RegistrationRequest(String respondToUrl) {
            respondTo = respondToUrl;
        }
    }

    @Before
    public void setUp() {
        http = new HttpMock();
        Context.http = http;
        bot = new RemoteBot(respondToUrl);
    }

    @Test
    public void botRegistration_respondToNotNull() {
        bot.registerForChannel(channelUrl);
        assertNotNull(http.lastContentSent);
    }

    @Test
    public void botRegistration_sendsRespondToAddress() {
        bot.registerForChannel(channelUrl);
        assertTrue(http.lastContentSent.contains(respondToUrl));
    }

    @Test
    public void botRegistration_validRegistrationJson() throws IOException {
        bot.registerForChannel(channelUrl);
        String content = http.lastContentSent;
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(content, RegistrationRequest.class);
    }

    static class RegistrationFailedException extends RuntimeException { }

    @Test(expected = RegistrationFailedException.class)
    public void serverIsNotReachable_errorIsThrown() {
        http.isAvailable = false;
        bot.registerForChannel(channelUrl);
    }

    @Test
    public void registrationFailed_retry3Times() {
        http.isAvailable = false;
        try {
            bot.registerForChannel(channelUrl);
        } catch (RegistrationFailedException ignored) { }

        assertEquals(3, http.requestCount);
    }

    class WrongConfigurationException extends RuntimeException {}

    @Test
    @Ignore
    public void registrationResponseWith4xxCode_specialExceptionThatShouldBeHandledByUI() {

    }

    @Test
    @Ignore
    public void registrationResponseWith4xxCode_exceptionMessageIsUseful() {
    }
}