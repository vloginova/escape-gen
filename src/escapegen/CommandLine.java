package escapegen;

import escapegen.commands.*;
import escapegen.context.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Vita Loginova
 */
public class CommandLine {

    private Game game;
    protected Map<String, Command> commands = new HashMap<>();

    public CommandLine(Game game) {
        this.game = game;

        putCommand(new Back(game), new Inventory(game), new Examine(game),
                new Lookaround(game), new Open(game), new Take(game), new Help(game));
    }

    private void putCommand(Command... toPut) {
        for (Command c : toPut)
            commands.put(c.toString(), c);
    }

    public void start() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!game.isOver()) {
                System.out.print(game.currentSpace().toString() + "> ");
                String command = br.readLine();

                if (command.equals("exit"))
                    return;

                String[] args = command.split(" ");

                Command c = commands.get(args[0]);

                if (c != null) {
                    c.run(args);
                } else {
                    System.out.println("Wrong command. Try 'help'");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Help extends Command {
        public Help(Game game) {
            super(game, "help", "help [command]\n\tOutput all available commands or specified command.");
        }

        @Override
        public void run(String[] args) {
            if (args.length != 1 && args.length != 2) {
                System.out.println(help());
                return;
            }

            if (args.length == 1) {
                System.out.println("Use 'help [command]' to see more detail about specific command.");
                commands.values().forEach(System.out::println);
            } else {
                Command command = commands.get(args[1]);
                if (command == null) {
                    System.out.println("There is no such command.");
                    System.out.println(help());
                }
                System.out.println(command.help());
            }
        }
    }
}
