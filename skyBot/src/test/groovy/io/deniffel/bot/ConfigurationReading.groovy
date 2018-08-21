package io.deniffel.bot

import io.deniffel.bot.skyBot.Configuration
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class ConfigurationReading {

    FSMock fs;

    @Before
    void setUp() throws Exception {
        fs = new FSMock()
    }

    @Test(expected = FileNotFoundException.class)
    void configPathNull_throwsException() throws Exception {
        fs.nextFileIsNotFound = true
        Configuration.read("/not/existing/path/aslfdjasdfj234dsf", fs)
    }

    @Test(expected = IllegalArgumentException.class)
    void emptyConfiguration_throwsIllegalArgumentException() {
        fs.nextFileReadResult = ""
        Configuration.read("config-file.yml", fs)
    }

    @Test
    void configWithUrlsGiven() {
        String master = "http://some-url.com"
        String own = "http://my-own-public-url-that-is-reacable-from-outside.com"

        String configYml = """
        urls:
            master: ${master}
            ownPublic: ${own}
        """
        fs.nextFileReadResult = configYml
        Configuration config = Configuration.read("config.yml", fs);

        assertEquals(master, config.urls.master)
        assertEquals(own, config.urls.ownPublic)
    }
}
