package escapegen.basics.lock;

import escapegen.model.Item;
import escapegen.model.Lock;
import escapegen.model.Tool;

import java.util.Collection;

/**
 * @author - Vita Loginova
 */
public class SimpleKeyLock extends Lock {

    public SimpleKeyLock(String name) {
        tools.put(name, new Tool(name, Item.Size.Small) {
            public void examine() {
                System.out.println("A little key.");
            }
        });
    }

    @Override
    protected boolean unlock(Collection<Tool> inventory) {
        return inventory.containsAll(tools.values());
    }

    @Override
    public String toString() {
        return "Small golden keyhole.";
    }
}
