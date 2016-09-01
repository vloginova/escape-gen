package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Examine extends Command {

    @Autowired
    private Game game;

    public Examine() {
        super("examine", "examine [item]\n\t" +
                "Examine specified item from the inventory or the current location.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1 && args.length != 2) {
            game.getUserIO().write(getHelp());
            return;
        }

        if (args.length == 1) {
            game.examineItem(game.getCurrentSpace());
            return;
        }

        String itemName = args[1];
        Item toExamine = game.getCurrentSpace().peekItem(itemName);
        if (toExamine == null) {
            toExamine = game.getInventory().get(itemName);
        }

        if (toExamine == null) {
            game.getUserIO().write("There is no " + itemName + ".");
            return;
        }

        game.examineItem(toExamine);
    }
}


