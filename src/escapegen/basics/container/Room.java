package escapegen.basics.container;

import escapegen.model.Container;
import escapegen.model.Item;

import java.util.Collection;

/**
 * @author - Vita Loginova
 */
public class Room extends Container {

    public Room(Collection<Item> items) {
        super("Room", Size.RoomSize);
        putAllItems(items);
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
