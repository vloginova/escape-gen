package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.Furniture;
import escapegen.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class Look extends Command {

    @Autowired
    private Game game;

    public Look() {
        super("look", "look side furniture\n\t" +
                "Use it for looking into some space of the furniture.\n\t" +
                "Try those from the following list:\n\t" +
                "\ton, under, left, right, back\n\t" +
                "Example: look under Table");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            game.getUserPrinter().println(getHelp());
            return;
        }

        String spaceString = args[1];
        String furnitureString = args[2];

        Item lookTo = game.getCurrentSpace().peekItem(furnitureString);

        if (lookTo == null) {
            game.getUserPrinter().println("There is no " + furnitureString);
            return;
        }

        if (!(lookTo instanceof Furniture)) {
            game.getUserPrinter().println("Can't look there.");
            return;
        }

        Furniture.Space space;
        try {
            space = Furniture.Space.getByName(spaceString);
        } catch (IllegalArgumentException e) {
            game.getUserPrinter().println("There is no such space.");
            return;
        }

        game.look((Furniture) lookTo, space);

        boolean result = game.look((Furniture) lookTo, space);
        if (!result) {
            game.getUserPrinter().println("Can't look there.");
        }
    }
}
