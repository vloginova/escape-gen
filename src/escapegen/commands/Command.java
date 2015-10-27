package escapegen.commands;

import escapegen.context.Game;

/**
 * {@code Command} represents the abstraction for control the game via
 * the command line interface. TODO: Generalize?
 *
 * @author - Vita Loginova
 */
public abstract class Command {

    private final String name;
    private final String help;
    protected Game game;

    protected Command(Game game, String name, String help) {
        if (name == null || help == null || game == null)
            throw new NullPointerException();

        this.name = name;
        this.help = help;
        this.game = game;
    }

    public final String help() {
        return help;
    }

    @Override
    public final String toString() {
        return name;
    }

    public abstract void run(String... args);
}
