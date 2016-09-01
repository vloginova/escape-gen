package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Item;

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
        if (args.length != 1 && args.length != 2) {
            System.out.println(help());
            return;
        }

        if (args.length == 1) {
            game.examineItem(game.currentSpace());
            return;
        }

        Item toExamine = game.currentSpace().peekItem(args[1]);
        if (toExamine == null) {
            toExamine = game.inventory().get(args[1]);
        }

        if (toExamine == null) {
            System.out.println("There is no " + args[1] + ".");
            return;
        }

        game.examineItem(toExamine);
    }
}


