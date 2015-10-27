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

    protected Container(String id, Size size) {
        super(id, size);
        parent = this;
    }

    /**
     * Puts the specified item in the container.
     * TODO: Add extra checks in order to forbid putting inappropriate objects.
     *
     * @param item to put.
     */
    public final void putItem(Item item) {
        this.items.put(item.toString(), item);
        if (Container.class.isInstance(item))
            ((Container) item).setParent(this);
    }

    /**
     * Puts all items in the container from the given collection.
     *
     * @param items {@code Collection} of items to add.
     */
    public final void putAllItems(Collection<Item> items) {
        items.forEach(this::putItem);
    }

    /**
     * Container might be placed in some other container which becomes its
     * parent. Default parent for container is itself.
     *
     * @return parent {@code Container}
     */
    public final Container getParent() {
        return parent;
    }

    /**
     * Sets parent container.
     *
     * @param parent {@code Container} to which this belongs.
     */
    protected final void setParent(Container parent) {
        if (parent == null)
            throw new IllegalArgumentException("Parent container cannot be null");

        this.parent = parent;
    }

    /**
     * Tries to open the Container using {@code tools}.
     *
     * @param tool A tool that is supposed to open the {@link Container#lock}
     * @return {@code true} if opening is succeed.
     */
    public final boolean tryOpen(Tool tool) {
        return lock == null || lock.tryUnlock(tool);
    }

    /**
     * Gets {@link Tool} from the container and removes it.
     *
     * @param name The string representation of the tool.
     * @return The corresponding {@code Tool}, {@code null} if there is no such
     * tool.
     */
    public Tool popTool(String name) {
        Item item = items.get(name);
        if (item == null || !Tool.class.isInstance(item))
            return null;

        return (Tool) items.remove(name);
    }

    public Item peekItem(String name) {
        return items.get(name);
    }

    /**
     * Used by {@link escapegen.context.Generator}, returns list of
     * {@link Tool}s necessary for opening the {@link Container#lock}
     *
     * @return list of {@link Tool}s necessary for opening the
     * {@link Container#lock}
     */
    public Collection<Tool> getLockTools() {
        if (lock == null) {
            return new LinkedList<>();
        }

        return lock.getTools();
    }

    /**
     * Returns {@link Container#items} subset including only objects of
     * {@link Container} type.
     *
     * @return List of {@link Container}s.
     */
    public List<Container> getContainers() {
        return items.values().stream()
                .filter(Container.class::isInstance)
                .map(item -> (Container) item)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Shows the internals.
     * TODO: must be opened.
     */
    public abstract void showContent();
}
