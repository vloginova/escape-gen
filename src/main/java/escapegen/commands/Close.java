package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.AbstractContainer;
import escapegen.model.Item;

/**
 * @author - Vita Loginova
 */
public class Close extends Command {

    public Close(Game game) {
        super(game, "close", "close [container]\n\t" +
                "Closes current container or, if parameter [container] is specified,\n\t" +
                "closes [container] which belongs to current.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 1 && args.length != 2) {
            System.out.println(help());
            return;
        }

        if (args.length == 1) {
            game.getCurrentSpace().close();
            game.setCurrentSpace(game.getCurrentSpace().getParent());
            return;
        }

        Item toClose = game.getCurrentSpace().peekItem(args[1]);

        if (toClose == null || !AbstractContainer.class.isInstance(toClose)) {
            System.out.println("There is no such container.");
            return;
        }

        ((AbstractContainer) toClose).close();
    }
}
