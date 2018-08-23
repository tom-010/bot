package io.deniffel.bot.chat;

import io.deniffel.bot.base.Response;
import io.deniffel.bot.skyBot.PluginBot;
import io.deniffel.bot.skyBot.PluginManager;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class LocalChat {

    private PluginManager manager;
    private PluginBot bot;

    public LocalChat() {
        this(PluginManager.build("chat/plugins"));
    }

    public LocalChat(PluginManager pluginManager) {
        manager = pluginManager;
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

            String response = handleInput(input);
            if(response != null)
                System.out.println("> "+ response);
            else
                System.out.println("<<empty>>");

            if("reload".equals(input))
                manager.refresh();

            if("exit".equals(input))
                break;
        }
    }

    protected void installFileWatcher() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                if(manager.hasPluginDirectoryChanged()) {
                    manager.refresh();
                    System.out.println("New file loaded");
                }
            }
        }, 0, (1000 * 1));
    }

    public String handleInput(String input) {
        Response response = bot.enter(input);
        if(response.isPresent())
            return (response.asString());
        else
            return null;
    }


}
