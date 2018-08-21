package io.deniffel.bot.console;

import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.PluginBot;
import io.deniffel.bot.skyBot.PluginManager;

import java.nio.file.Paths;
import java.util.Scanner;

public class ConsoleApp {

    private PluginManager manager;
    private PluginBot bot;

    public ConsoleApp() {
        manager = PluginManager.build("consoleApp/plugins");
        bot = new PluginBot(manager);
    }

    public static void main(String... args) {
        new ConsoleApp().mainLoop();
    }

    private void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while((input = scanner.nextLine()) != null) {
            handleInput(input);

            if("reload".equals(input))
                manager.init();

            if("exit".equals(input))
                break;
        }
    }

    private void handleInput(String input) {
        Response response = bot.enter(input);
        if(response.isPresent())
            System.out.println("> " + response.response());
        else
            System.out.println("<<empty>>");
    }


}
