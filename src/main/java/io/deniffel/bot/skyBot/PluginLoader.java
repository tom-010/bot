package io.deniffel.bot.skyBot;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {

    static void load() throws ClassNotFoundException, MalformedURLException {
        File f = new File( "/home/ravinder/Desktop/mysql-connector-java-5.1.18-bin.jar" );
        URLClassLoader urlCl = new URLClassLoader( new URL[] { f.toURL() }, System.class.getClassLoader() );

    }

}
