package io.deniffel.bot.console;

import java.util.Scanner;

public class ConsoleApp {
    public static void main(String... args) {
        new ConsoleApp().mainLoop();
    }

    private void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while((input = scanner.nextLine()) != null) {
            handleInput(input);

            if("exit".equals(input))
                break;
        }
    }

    private void handleInput(String input) {
        System.out.println(input);
    }


}
