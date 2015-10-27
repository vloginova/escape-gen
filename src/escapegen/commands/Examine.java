package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Examine extends Command {

    public Examine(Game game) {
        super(game, "examine", "examine tool\n\t" +
                "Examine specified tool that you have in your inventory.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 2) {
            System.out.println(help());
            return;
        }

        Tool tool = game.inventory().get(args[1]);

        if (tool == null) {
            System.out.println("You do not have " + args[1] + ".");
            return;
        }

        tool.examine();
    }
}


