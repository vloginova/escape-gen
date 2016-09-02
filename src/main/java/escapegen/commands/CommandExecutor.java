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
            game.getUserIO().write("Wrong command. Try 'help'");
        }
    }

    private void echo(String command) {
        game.getUserIO().write(game.getPathToCurrentLocation() + " > " + command);
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
                game.getUserIO().write(getHelp());
                return;
            }

            if (args.length == 1) {
                game.getUserIO().write("Use 'help [command]' to see more detail about specific command.");
                commands.values().forEach(s -> game.getUserIO().write(s.getName()));
            } else {
                Command command = commands.get(args[1]);
                if (command == null) {
                    game.getUserIO().write("There is no such command.");
                    game.getUserIO().write(getHelp());
                }
                game.getUserIO().write(command.getHelp());
            }
        }
    }
}
