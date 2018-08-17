package io.deniffel.bot.console;

import io.deniffel.bot.base.Bot;
import io.deniffel.bot.skyBot.EchoBot;
import io.deniffel.bot.skyBot.SkyBot;

public class ConsoleApp {

    Bot bot = new EchoBot();
    public static boolean startFinished = false;

    public static void main(String... args) {
        startFinished = true;
    }

}
