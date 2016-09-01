package escapegen.commands;

import lombok.Getter;

/**
 * {@code Command} represents the abstraction for control the game via
 * the command line interface. TODO: Generalize?
 *
 * @author - Vita Loginova
 */
public abstract class Command {

    @Getter
    private final String name;
    @Getter
    private final String help;

    protected Command(String name, String help) {
        if (name == null || help == null)
            throw new NullPointerException();

        this.name = name;
        this.help = help;
    }

    @Override
    public final String toString() {
        return name;
    }

    public abstract void execute(String... args);
}
