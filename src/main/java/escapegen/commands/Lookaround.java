package escapegen.commands;

import escapegen.context.Game;

/**
 * @author - Vita Loginova
 */
public class Lookaround extends Command {

    public Lookaround(Game game) {
        super(game, "lookaround", "lookaround\n\tOutputs all objects reachable in the current area.");
    }

    @Override
    public void run(String... args) {
        if (args.length != 1) {
            System.out.println(help());
            return;
        }

        game.showContent(game.currentSpace());
    }
}
