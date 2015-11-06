package escapegen.basics.container;

import escapegen.model.Container;
import escapegen.model.Item;

import java.util.Collection;

/**
 * @author - Vita Loginova
 */
public class Room extends Container {

    public Room(Collection<Item> items) {
        super("Room");
        setSize(Size.RoomSize);
        putAllItems(items);
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }

    @Override
    public void examine() {
        System.out.println("A large light room. It's pretty, but I need to get out of here.");
    }
}
