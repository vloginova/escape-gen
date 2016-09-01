package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Item;

/**
 * @author - Vita Loginova
 */
public class Apply extends Command {
    public Apply(Game game) {
        super(game, "apply", "apply one to other\n\t" +
                "Tries to open the specified object using tool (optional).\n\t" +
                "Example: open table using key");
    }

    @Override
    public void run(String... args) {
        if (args.length != 4 || !args[2].equals("to")) {
            System.out.println(help());
            return;
        }

        Item applyWhat = game.inventory().get(args[1]);
        applyWhat = applyWhat == null ? game.currentSpace().peekItem(args[3]) : applyWhat;

        Item applyTo = game.inventory().get(args[1]);
        applyTo = applyTo == null ? game.currentSpace().peekItem(args[3]) : applyTo;

        if (applyWhat == null) {
            System.out.println("There is no " + args[3] + ".");
            return;
        }

        if (applyTo == null) {
            System.out.println("There is no " + args[1] + ".");
            return;
        }

        game.apply(applyTo, applyWhat);
    }
}
