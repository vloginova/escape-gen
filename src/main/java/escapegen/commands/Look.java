package escapegen.commands;

import escapegen.context.Game;
import escapegen.model.AbstractContainer;
import escapegen.model.Furniture;
import escapegen.model.Item;

/**
 * @author - Vita Loginova
 */
public class Look extends Command {

    public Look(Game game) {
        super(game, "look", "look side furniture\n\t" +
                "Use it for looking into some space of the furniture.\n\t" +
                "Try those from the following list:\n\t" +
                "\ton, under, left, right, back\n\t" +
                "Example: look under Table");
    }

    @Override
    public void run(String... args) {
        if (args.length != 3) {
            System.out.println(help());
            return;
        }

        String spaceString = args[1];
        String furnitureString = args[2];

        Item lookTo = game.currentSpace().peekItem(furnitureString);

        if (lookTo == null) {
            System.out.println("There is no " + furnitureString);
            return;
        }

        Furniture.Space space;
        switch (spaceString) {
            case "on":
                space = Furniture.Space.On;
                break;
            case "back":
                space = Furniture.Space.Back;
                break;
            case "left":
                space = Furniture.Space.LeftSide;
                break;
            case "right":
                space = Furniture.Space.RightSide;
                break;
            case "under":
                space = Furniture.Space.Under;
                break;
            default:
                System.out.println("There is no such space.");
                return;
        }

        if (!Furniture.class.isInstance(lookTo)) {
            System.out.println("Can't look there.");
            return;
        }

        AbstractContainer container = ((Furniture) lookTo).getSpace(space);

        if (container == null) {
            System.out.println("Can't look there.");
            return;
        }

        game.setCurrentSpace(container);
        game.showContent(container);
    }
}
