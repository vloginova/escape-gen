package escapegen.basics;

import escapegen.model.Container;
import escapegen.model.Item;

import java.util.Collection;

/**
 * @author - Vita Loginova
 */
public class Room extends Container {

    public Room(Collection<Item> items) {
        super("Room", Size.RoomSize);
        setParent(this);
        putAllItems(items);
    }

    @Override
    protected void showContent() {
        throw new RuntimeException("Unacceptable operation");
    }
}
