package io.deniffel.bot.console;

import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.PluginBot;
import io.deniffel.bot.skyBot.PluginManager;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class LocalChat {

    private PluginManager manager;
    private PluginBot bot;

    LocalChat() {
        manager = PluginManager.build("localChat/plugins");
        bot = new PluginBot(manager);
    }

    public static void main(String... args) {
        new LocalChat().mainLoop();
    }

    private void mainLoop() {
        installFileWatcher();

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

    private void installFileWatcher() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                if(manager.hasPluginDirectoryChanged()) {
                    manager.init();
                    System.out.println("New file loaded");
                }
            }
        }, 0, (1000 * 1));
    }

    private void handleInput(String input) {
        Response response = bot.enter(input);
        if(response.isPresent())
            System.out.println("> " + response.asString());
        else
            System.out.println("<<empty>>");
    }


}
