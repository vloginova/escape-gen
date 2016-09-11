package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Apply extends Command {

    @Autowired
    private Game game;

    public Apply() {
        super("apply", "apply one to other\n\t" +
                "Tries to open the specified object using tool (optional).\n\t" +
                "Example: open table using key");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 4 || !args[2].equals("to")) {
            game.getUserPrinter().println(getHelp());
            return;
        }

        String applyWhatId = args[1];
        String applyToId = args[3];

        Item applyWhat = getItemFromGameContext(applyWhatId);
        if (applyWhat == null) {
            game.getUserPrinter().println("There is no " + applyWhatId + ".");
            return;
        }

        Item applyTo = getItemFromGameContext(applyToId);
        if (applyTo == null) {
            game.getUserPrinter().println("There is no " + applyToId + ".");
            return;
        }

        game.apply(applyTo, applyWhat);
    }

    private Item getItemFromGameContext(String name) {
        Item item = game.getInventory().get(name);
        return item == null
                ? game.getCurrentSpace().peekItem(name)
                : item;
    }
}
