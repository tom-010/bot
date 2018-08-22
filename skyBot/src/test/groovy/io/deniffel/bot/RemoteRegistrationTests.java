package io.deniffel.bot;

import io.deniffel.bot.skyBot.Configuration;
import io.deniffel.bot.skyBot.Master;
import io.deniffel.bot.skyBot.RegistrationRequest;
import io.deniffel.bot.skyBot.RemoteBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteRegistrationTests {

    private Master master;
    private HttpMock http;
    private RemoteBot worker;
    private FSMock fs;
    private Configuration config;

    class MasterMock implements Master {

        @Override
        public void register(RegistrationRequest registration) {

        }
    }

    @Before
    public void setUp() throws Exception {
        config = buildConfig();
        master = new MasterMock();
        http = new HttpMock();
        worker = new RemoteBot(http, config);
        fs = new FSMock();
    }

    private Configuration buildConfig() {
        config = new Configuration();
        config.urls = new Configuration.Urls();
        config.urls.master = "http://master";
        config.urls.ownPublic = "http://ownUrl";
        return config;
    }

    @Test
    public void registration_sendsHttpRequestToMastersUrl() {
        worker.sendRegistrationToMaster();
        assertEquals(config.urls.master, http.lastUrlUsed);
    }

    @Test
    public void registration_sendsTheListsOfRegexes() {


    }
}
