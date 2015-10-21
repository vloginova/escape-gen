package escapegen;

import escapegen.context.Game;
import escapegen.context.Generator;

/**
 * @author - Vita Loginova
 */
public class Launcher {

    public static void main(String[] args) {
        System.out.println("Where am I?");
        Generator gen = new Generator();
        Game game = gen.generate(10);
        new CommandLine(game).start();
    }
}
