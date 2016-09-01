package escapegen.commands;

import escapegen.context.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Inventory extends Command {

    @Autowired
    private Game game;

    public Inventory() {
        super("inventory", "inventory\n\tOutputs all tools you have in your inventory.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            game.getUserIO().write(getHelp());
            return;
        }

        game.getInventory().values().forEach(i -> i.getId());
    }
}
