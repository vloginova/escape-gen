package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Back extends Command {

    public Back(Game game) {
        super(game, "back", "back [level]\n\tGo back on less specific level.\n\t" +
                "Set [level] to apply this command [level] times.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 1 && args.length != 2) {
            System.out.println(help());
            return;
        }

        int back = 1;
        if (args.length == 2) {
            try {
                back = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println(help());
                return;
            }
        }

        Container currentSpace = game.getCurrentSpace();

        while (back-- > 0) {
            Container parent = currentSpace.getParent();

            if (parent == currentSpace)
                break;

            currentSpace = parent;
        }

        game.setCurrentSpace(currentSpace);
    }
}
