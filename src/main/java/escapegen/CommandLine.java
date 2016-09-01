package escapegen;

import escapegen.commands.Command;
import escapegen.commands.CommandExecutor;
import escapegen.context.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Vita Loginova
 */
@Component
public class CommandLine {
    @Autowired
    private Game game;
    @Autowired
    private CommandExecutor executor;

    protected Map<String, Command> commands = new HashMap<>();

    public void start() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!game.isGameOver()) {
                game.getUserIO().write(game.getCurrentSpace().getId() + "> ");
                String command = br.readLine();

                if (command.equals("exit"))
                    return;

                executor.executeCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
