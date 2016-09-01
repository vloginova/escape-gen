package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Back extends Command {

    @Autowired
    private Game game;

    public Back() {
        super("back", "back [level]\n\tGo back on less specific level.\n\t" +
                "Set [level] to apply this command [level] times.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1 && args.length != 2) {
            game.getUserIO().write(getHelp());
            return;
        }

        int back = 1;
        if (args.length == 2) {
            try {
                back = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                game.getUserIO().write(getHelp());
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
