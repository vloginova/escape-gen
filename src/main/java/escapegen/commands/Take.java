package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Take extends Command {

    public Take(Game game) {
        super(game, "take", "take\n\tTake the specified tool from the current space.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 2) {
            System.out.println(help());
            return;
        }

        Tool tool = game.getCurrentSpace().popTool(args[1]);

        if (tool == null) {
            System.out.println("There is no such thing.");
            return;
        }

        game.getInventory().put(tool.getId(), tool);
    }
}
