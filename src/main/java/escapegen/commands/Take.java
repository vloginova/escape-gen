package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Take extends Command {

    @Autowired
    private Game game;

    public Take() {
        super("take", "take\n\tTake the specified tool from the current space.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            game.getUserIO().write(getHelp());
            return;
        }

        Tool tool = game.getCurrentSpace().popTool(args[1]);

        if (tool == null) {
            game.getUserIO().write("There is no such thing.");
            return;
        }

        game.getInventory().put(tool.getId(), tool);
    }
}
