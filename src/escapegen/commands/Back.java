package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Back extends Command {

    public Back(Game game) {
        super(game, "back", "back\n\tGo back on less specific place.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 1) {
            System.out.println(help());
            return;
        }

        Container currentSpace = game.currentSpace();
        game.setCurrentSpace(currentSpace.getParent());
    }
}
