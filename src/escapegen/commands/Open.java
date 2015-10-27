package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Container;
import escapegen.model.Item;
import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Open extends Command {

    public Open(Game game) {
        super(game, "open", "open object [using tool]\n\t" +
                "Tries to open the specified object using tool (optional).\n\t" +
                "Example: open table using key");
    }

    @Override
    public void run(String... args) {
        if (args.length != 2 && !(args.length == 4 && args[2].equals("using"))) {
            System.out.println(help());
            return;
        }

        Item openTo = game.currentSpace().peekItem(args[1]);

        if (openTo == null) {
            System.out.println("There is no " + args[1] + ".");
            return;
        }

        if (!Container.class.isInstance(openTo)) {
            System.out.println("Cannot be opened at all.");
            return;
        }

        Tool tool = null;

        if (args.length == 4) {
            tool = game.inventory().get(args[3]);
            if (tool == null) {
                System.out.println("There is no " + args[3] + ".");
                return;
            }
        }

        Container c = (Container) openTo;
        if (!c.tryOpen(tool)) {
            System.out.println("Opening failed.");
            return;
        }

        if (tool != null && tool.isUsed())
            game.inventory().remove(args[3]);

        c.showContent();
        game.setCurrentSpace(c);

        if (game.getGoal() == c) {
            game.endGame();
        }
    }
}
