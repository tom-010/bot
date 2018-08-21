package io.deniffel.bot.skyBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class Configuration {

    public Urls urls;

    public static Configuration read(String path, Filesystem fs) throws RuntimeException, IOException {
        String fileContent = fs.read(Paths.get(path));
        if("".equals(fileContent))
            throw new IllegalArgumentException("Config file is empty");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(fileContent, Configuration.class);
    }

    public static class Urls {
        public String master;
        public String ownPublic;
    }

}
