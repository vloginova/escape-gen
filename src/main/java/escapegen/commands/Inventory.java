package escapegen.commands;

import escapegen.context.Game;

/**
 * @author - Vita Loginova
 */
public class Inventory extends Command {

    public Inventory(Game game) {
        super(game, "inventory", "inventory\n\tOutputs all tools you have in your inventory.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 1) {
            System.out.println(help());
            return;
        }

        game.inventory().values().forEach(i -> game.examineItem(i));
    }
}
