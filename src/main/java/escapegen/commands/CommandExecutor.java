package escapegen.commands;

import escapegen.context.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vita on 9/2/16.
 */
@Component
public class CommandExecutor {
    @Autowired
    private Game game;
    protected Map<String, Command> commands = new HashMap<>();

    @Autowired
    public void registerCommands(List<Command> commandList){
        for (Command command : commandList) {
            if (commands.containsKey(command.getName())) {
                throw new IllegalStateException("command " + command.getName() + " already registered");
            }
            commands.put(command.getName(), command);
        }
    }

    public void executeCommand(String command) {
        echo(command);

        String[] args = command.trim().split(" ");

        Command c = commands.get(args[0]);

        if (c != null) {
            c.execute(args);
        } else {
            game.getUserPrinter().println("Wrong command. Try 'help'");
        }

        invite();
    }

    public void invite() {
        if (game.getState() == Game.State.WAITING_FOR_COMMAND)
            game.getUserPrinter().print(game.getPathToCurrentLocation() + " > ");
    }

    private void echo(String command) {
        game.getUserPrinter().println(command);
    }

    public List<String> getAllCommands() {
        return new ArrayList<>(commands.keySet());
    }

    @Component
    private class Help extends Command {
        public Help() {
            super("help", "help [command]\n\tOutput all available commands or specified command.");
        }

        @Override
        public void execute(String[] args) {
            if (args.length != 1 && args.length != 2) {
                game.getUserPrinter().println(getHelp());
                return;
            }

            if (args.length == 1) {
                game.getUserPrinter().println("Use 'help [command]' to see more detail about specific command.");
                commands.values().forEach(s -> game.getUserPrinter().println(s.getName()));
            } else {
                Command command = commands.get(args[1]);
                if (command == null) {
                    game.getUserPrinter().println("There is no such command.");
                    game.getUserPrinter().println(getHelp());
                }
                game.getUserPrinter().println(command.getHelp());
            }
        }
    }
}
