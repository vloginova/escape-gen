package escapegen.basics.lock;

import escapegen.model.Item;
import escapegen.model.Lock;
import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class SimpleKeyLock extends Lock {

    public SimpleKeyLock(String name) {
        Tool key = new Tool(name) {
            public void examine() {
                System.out.println("A little key.");
            }
        };
        key.setSize(Item.Size.Small);

        tools.add(key);
    }

    @Override
    protected boolean unlock(Tool tool) {
        boolean unlocked = tools.contains(tool);

        if (unlocked)
            tool.use();

        return unlocked;
    }

    @Override
    public String toString() {
        return "Small golden keyhole.";
    }
}
