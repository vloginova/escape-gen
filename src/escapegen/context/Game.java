package escapegen.context;

import escapegen.model.Container;
import escapegen.model.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game represents the whole context for the concrete game.
 *
 * @author - Vita Loginova
 */
public class Game {

    private Map<String, Tool> inventory;
    private Container currentSpace;
    private Container goal;
    private boolean isGameOver = false;
    private Random rand;

    public Game() {
        inventory = new HashMap<>();
        rand = new Random();
    }

    public Map<String, Tool> inventory() {
        return inventory;
    }

    public Container currentSpace() {
        return currentSpace;
    }

    public void setCurrentSpace(Container container) {
        currentSpace = container;
    }

    public boolean isOver() {
        return isGameOver;
    }

    public void endGame() {
        isGameOver = true;
    }

    public Random getRandom() { return rand; }

    public Container getGoal() {
        return goal;
    }

    public void setGoal(Container goal) {
        this.goal = goal;
    }
}
