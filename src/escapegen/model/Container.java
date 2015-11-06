package escapegen.model;

import java.util.*;

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
    protected List<Container> depends = new LinkedList<>();
    private Container parent;
    private boolean isOpened;

    protected Container(String id) {
        super(id);
        parent = this;
        isOpened = false;
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
        if (!isVisible()) {
            throw new RuntimeException("Trying to open invisible object");
        }

        isOpened = isOpened || lock == null || lock.tryUnlock(tool);
        return isOpened;
    }

    /**
     * Closes the Container.
     */
    public void close() {
        isOpened = false;
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

    /**
     * Gets {@link Tool} from the container.
     *
     * @param name The string representation of the tool.
     * @return The corresponding {@code Tool}, {@code null} if there is no such
     * tool.
     */
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
     * @return {@code true} if the Container is opened, {@code false} otherwise.
     */
    public boolean isOpened() { return isOpened; }

    /**
     * Shows the internals.
     * TODO: must be opened.
     */
    public abstract void showContent();

    /**
     * Some containers might be dependant on another. Container A depends on
     * container B if some actions that the player is able to perform on A
     * become available only after opening B. This relationship is transitive,
     * asymmetric and nonreflexive.<br>
     * <br>
     * This information is a hint for {@link escapegen.context.Generator}
     * necessary for avoiding unsolvable cycles. For example, in order to
     * be able to do something with the false bottom of the box, you have to
     * open the box. So that tools for opening the box mustn't be placed in
     * the false bottom.
     *
     * @param container {@link Container} on witch this depends.
     */
    public void addDependencies(Container container) {
        if (container == this)
            throw new RuntimeException("Reflexivity is forbidden.");

        if (container.dependsOn(this))
            throw new RuntimeException("Circular dependence detected.");

        depends.add(container);
    }

    /**
     * @param tool Tool to check if its unlocking some Container on which
     *             this depends.
     * @return {@code true} if this depends on {@code tool}, {@code false}
     * otherwise.
     * @see #addDependencies(Container) for more details about dependence
     * relationship
     */
    public boolean dependsOn(Tool tool) {
        return depends.stream()
               .reduce(false,
                       (res, c) -> res || c.dependsOn(tool) ||
                               c.getLockTools().contains(tool),
                       (res1, res2) -> res1 || res2);
    }

    /**
     * @param container Container to check if this depends on it.
     * @return {@code true} if this depends on {@code tool}, {@code false}
     * otherwise.
     * @see #addDependencies(Container) for more details about dependence
     * relationship
     */
    public boolean dependsOn(Container container) {
        if (depends.contains(container))
            return true;

        return depends.stream()
               .reduce(false,
                       (res, c) -> res || c.dependsOn(container),
                       (res1, res2) -> res1 || res2);
    }
}
