package io.deniffel.bot.console;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StartupTests {

    static boolean started = false;

    @Before
    public void setUp() throws Exception {
        started = false;
    }

    @Test
    public void canInstanciateConsoleApp() {
        new LocalChat(); // No exception
    }

    @Test
    public void applicationStartswithin5Seconds() throws InterruptedException {
//        Thread thread = new Thread(() -> {
//            ConsoleApp.main();
//            started = true;
//        });
//        thread.start();
//        thread.join(5*SECONDS);
//        assertTrue(started);
    }

    static Long SECONDS = 1000L;
}
