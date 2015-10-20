package escapegen.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Container can hold tools or other containers. Container represents furniture,
 * its parts, other objects. To access in the game the internal space of the
 * container it is necessary to unlock its {@link Container#lock}.
 *
 * @author - Vita Loginova
 */
public abstract class Container extends Item {

    protected Lock lock;
    protected Map<String, Item> items = new HashMap<>();
    private Container parent;

    protected Container(String id, Size size) { super(id, size); }

    /**
     * Puts the specified item in the container.
     * TODO: Add extra checks in order to forbid putting inappropriate objects.
     *
     * @param item to put.
     */
    public void putItem(Item item) {
        this.items.put(item.toString(), item);
        if (Container.class.isInstance(item))
            ((Container) item).setParent(this);
    }

    /**
     * Puts all items in the container from the given collection.
     *
     * @param items {@code Collection} of items to add.
     */
    public void putAllItems(Collection<Item> items) {
        items.forEach(this::putItem);
    }

    /**
     * All containers are places in some other container. There is only one
     * exception for the outermost one, which parent is itself.
     *
     * @return parent {@code Container}
     */
    public Container getParent() {
        return parent;
    }

    /**
     * Sets parent container. This method should be used only during
     * construction stage.
     *
     * @param parent {@code Container} to which this belongs.
     */
    public void setParent(Container parent) {
        this.parent = parent;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    /**
     * Tries to open the Container using {@code tools}.
     * TODO: remove tools from the inventory.
     *
     * @param tools A collection of tools that is supposed to open the
     *              {@link Container#lock}
     * @return {@code true} if opening is succeed.
     */
    public boolean tryOpen(Collection<Tool> tools) {
        if (lock == null || lock.isUnlocked() || lock.tryUnlock(tools)) {
            showContent();
            return true;
        }

        return false;
    }

    /**
     * Gets {@link Tool} from the container and removes it.
     *
     * @param name The string representation of the tool.
     * @return the corresponding {@code Tool}, {@code null} if there is no such
     *         tool.
     */
    public Tool pickTool(String name) {
        Item item = items.get(name);
        if (item == null || !Tool.class.isInstance(item))
            return null;

        return (Tool) items.remove(name);
    }

    public Collection<Tool> getTools() {
        if (lock == null)
            return new LinkedList<>();

        return this.lock.getTools().values();
    }

    public boolean isFull() { return false; }

    public List<Container> getContainers() {
        return items.values().stream().filter(Container.class::isInstance)
                .map(item -> (Container) item).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * In case the container is unlocked, shows the internals.
     */
    protected abstract void showContent();
}
