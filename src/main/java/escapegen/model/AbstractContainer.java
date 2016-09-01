package escapegen.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * AbstractContainer can hold tools or other containers. AbstractContainer represents furniture,
 * its parts, other objects. To access in the game the internal space of the
 * container it is necessary to unlock its {@link AbstractContainer#lock}.
 *
 * @author - Vita Loginova
 */
public abstract class AbstractContainer extends AbstractItem implements Container {

    @Getter @Setter
    ContainerDescription<?> description;
    protected Lock lock;
    protected Map<String, Item> items = new HashMap<>();
    protected List<AbstractContainer> depends = new LinkedList<>();
    private AbstractContainer parent;
    private boolean isOpened;

    protected AbstractContainer() {
        parent = this;
        isOpened = false;
    }

    @Override
    public Collection<Item> getItems() {
        //TODO
        return items.values();
    }

    @Override
    public Collection<Item> removeItems() {
        //todo
        items.clear();
        return items.values();
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    @Override
    public boolean isEmpty() {
        //todo
        return items.isEmpty();
    }

    /**
     * Puts the specified item in the container.
     * TODO: Add extra checks in order to forbid putting inappropriate objects.
     *
     * @param item to put.
     */
    public void putItem(Item item) {
        this.items.put(item.toString(), item);
        if (AbstractContainer.class.isInstance(item))
            ((AbstractContainer) item).setParent(this);
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
     * AbstractContainer might be placed in some other container which becomes its
     * parent. Default parent for container is itself.
     *
     * @return parent {@code AbstractContainer}
     */
    public final AbstractContainer getParent() {
        return parent;
    }

    /**
     * Sets parent container.
     *
     * @param parent {@code AbstractContainer} to which this belongs.
     */
    protected final void setParent(AbstractContainer parent) {
        if (parent == null)
            throw new IllegalArgumentException("Parent container cannot be null");

        this.parent = parent;
    }

    /**
     * Tries to open the AbstractContainer using {@code tools}.
     *
     * @return {@code true} if opening is succeed.
     */
    public boolean tryOpen() {
        if (!isVisible()) {
            throw new RuntimeException("Trying to open invisible object");
        }

        isOpened = isOpened || lock == null;
        return isOpened;
    }

    /**
     * Closes the AbstractContainer.
     */
    public void close() {
        isOpened = false;
    }

    /**
     * Gets {@link Tool} from the container and removes it.
     *
     * @param name The string representation of the tool.
     * @return The corresponding {@code Tool}, {@code null} if there is no such tool.
     */
    public Tool popTool(String name) {
        Item item = items.get(name);
        if (item == null || !Tool.class.isInstance(item))
            return null;

        return (Tool) items.remove(name);
    }

    /**
     * Gets {@link Item} from the container.
     *
     * @param name The string representation of the tool.
     * @return The corresponding {@code Item}, {@code null} if there is no such tool.
     */
    public Item peekItem(String name) {
        return items.get(name);
    }

    /**
     * Used by {@link escapegen.context.Generator}, returns list of {@link Tool}s necessary for opening the
     * {@link AbstractContainer#lock}
     *
     * @return list of {@link Tool}s necessary for opening the {@link AbstractContainer#lock}
     */
    public Collection<Tool> getLockTools() {
        if (lock == null) {
            return new LinkedList<>();
        }

        return lock.getTools();
    }

    /**
     * @return {@code true} if the AbstractContainer is opened, {@code false} otherwise.
     */
    public boolean isOpened() { return isOpened; }

    /**
     * Some containers might be dependant on another. AbstractContainer A depends on
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
     * @param container {@link AbstractContainer} on witch this depends.
     */
    public void addDependency(AbstractContainer container) {
        if (container == this)
            throw new RuntimeException("Reflexivity is forbidden.");

        if (container.dependsOn(this))
            throw new RuntimeException("Circular dependence detected.");

        depends.add(container);
    }

    /**
     * @param tool Tool to check if its unlocking some AbstractContainer on which this depends.
     * @return {@code true} if this depends on {@code tool}, {@code false} otherwise.
     * @see #addDependency(AbstractContainer) for more details about dependence relationship
     */
    public boolean dependsOn(Tool tool) {
        return depends.stream()
               .reduce(false,
                       (res, c) -> res || c.dependsOn(tool) || c.getLockTools().contains(tool),
                       (res1, res2) -> res1 || res2);
    }

    /**
     * @param container AbstractContainer to check if this depends on it.
     * @return {@code true} if this depends on {@code tool}, {@code false} otherwise.
     * @see #addDependency(AbstractContainer) for more details about dependence relationship
     */
    public boolean dependsOn(AbstractContainer container) {
        if (depends.contains(container))
            return true;

        return depends.stream()
               .reduce(false,
                       (res, c) -> res || c.dependsOn(container),
                       (res1, res2) -> res1 || res2);
    }
}
