package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.AbstractContainer;
import escapegen.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Close extends Command {

    @Autowired
    private Game game;

    public Close() {
        super("close", "close [container]\n\t" +
                "Closes current container or, if parameter [container] is specified,\n\t" +
                "closes [container] which belongs to current.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1 && args.length != 2) {
            game.getUserIO().write(getHelp());
            return;
        }

        if (args.length == 1) {
            game.getCurrentSpace().close();
            game.setCurrentSpace(game.getCurrentSpace().getParent());
            return;
        }

        Item toClose = game.getCurrentSpace().peekItem(args[1]);

        if (toClose == null || !AbstractContainer.class.isInstance(toClose)) {
            game.getUserIO().write("There is no such container.");
            return;
        }

        ((AbstractContainer) toClose).close();
    }
}
