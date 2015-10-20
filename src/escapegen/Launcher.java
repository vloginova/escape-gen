package escapegen;

import escapegen.context.Game;

/**
 * @author - Vita Loginova
 */
public class Launcher {

    public static void main(String[] args) {
        System.out.println("Hmm, my laptop is in the safe. But I forgot a code... I believe I've wrote it somewhere.");
        Game game = new Game();
        (new CommandLine(game)).start();
    }
}
