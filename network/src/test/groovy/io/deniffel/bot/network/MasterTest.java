package io.deniffel.bot.network;

import org.junit.Before;
import org.junit.Test;

public class MasterTest {

    private Master master;

    @Before
    public void setUp() throws Exception {
        master = Master.build(); // Assumption: Master (REST-Endpoint) is properly running and reachable
    }

    @Test
    public void nothing() {

    }
}
