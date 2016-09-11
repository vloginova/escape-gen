package escapegen.commands;

import escapegen.context.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Lookaround extends Command {

    @Autowired
    private Game game;

    public Lookaround() {
        super("lookaround", "lookaround\n\tOutputs all objects reachable in the current area.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            game.getUserPrinter().println(getHelp());
            return;
        }

        game.showContent(game.getCurrentSpace());
    }
}
