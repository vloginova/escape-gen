package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.AbstractContainer;
import escapegen.model.Item;
import escapegen.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Open extends Command {

    @Autowired
    private Game game;

    public Open() {
        super("open", "open container [using tool]\n\t" +
                "Tries to open the specified container using tool (optional).\n\t" +
                "Example: open table using key");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 2 && !(args.length == 4 && args[2].equals("using"))) {
            game.getUserIO().write(getHelp());
            return;
        }

        Item openTo = game.getCurrentSpace().peekItem(args[1]);

        if (openTo == null) {
            game.getUserIO().write("There is no " + args[1] + ".");
            return;
        }

        if (!AbstractContainer.class.isInstance(openTo)) {
            game.getUserIO().write("Cannot be opened at all.");
            return;
        }

        Tool tool = null;

        if (args.length == 4) {
            tool = game.getInventory().get(args[3]);
            if (tool == null) {
                game.getUserIO().write("There is no " + args[3] + ".");
                return;
            }
        }

        AbstractContainer c = (AbstractContainer) openTo;
        if (!game.openContainer(c, tool)) {
            return;
        }

        if (tool != null && tool.isUsed())
            game.getInventory().remove(args[3]);

        game.showContent(c);
        game.setCurrentSpace(c);

        if (game.getGoal() == c) {
            game.setGameOver(true);
        }
    }
}
