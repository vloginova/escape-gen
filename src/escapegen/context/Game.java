package escapegen.context;

import escapegen.model.Container;
import escapegen.model.Tool;
import escapegen.basics.Room;
import escapegen.basics.Vault;
import escapegen.model.Item;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Game represents the whole context for the concrete game.
 *
 * @author - Vita Loginova
 */
public class Game {

    private Map<String, Tool> inventory;
    private Container currentSpace;
    private boolean isGameOver = false;

    public Game() {
        List<Item> furniture = new LinkedList<>();
        inventory = new HashMap<>();

        Vault vault = new Vault();
        furniture.add(vault);

        vault.getTools().forEach(t -> inventory.put(t.toString(), t));

        currentSpace = new Room(furniture);
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
}
