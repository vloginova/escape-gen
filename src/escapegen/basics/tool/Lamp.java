package escapegen.basics.tool;

import escapegen.model.Item;
import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Lamp extends Tool {
    public Lamp(String id) {
        super(id);
        setSize(Size.Small);
    }

    @Override
    public void examine() {

    }

    @Override
    public boolean apply(Item item) {
        return item.apply(this);
    }
}
