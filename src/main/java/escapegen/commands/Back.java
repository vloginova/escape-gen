package escapegen.commands;

import escapegen.context.Game;
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
            game.getUserPrinter().println(getHelp());
            return;
        }

        int back = 1;
        if (args.length == 2) {
            try {
                back = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                game.getUserPrinter().println(getHelp());
                return;
            }
        }

        while (back-- > 0 && game.getRoom() != game.getCurrentSpace()) {
            game.back();
        }
    }
}
