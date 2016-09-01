package escapegen.model;

import java.util.Collection;

/**
 * @author - Vita Loginova
 */
public interface Container extends Item {
    Collection<Item> getItems();
    Collection<Item> removeItems();
    Item peekItem(String name);
    Container getParent();
    void close();
    boolean isEmpty();
    Lock getLock();
    Tool popTool(String name);
    boolean tryOpen();
}
