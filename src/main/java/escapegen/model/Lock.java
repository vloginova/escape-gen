package escapegen.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Locks cannot be self-independent, they are always a part of some
 * {@link AbstractContainer}. They are always created being locked. Also locks are
 * associated with its tool (= key) or a set of tools.
 *
 * @author - Vita Loginova
 */
public abstract class Lock extends AbstractItem {
    @Getter @Setter
    LockDescription<?> description;
    @Getter
    protected boolean isUnlocked = false;
    protected Collection<Tool> tools = new LinkedList<>();

    /**
     * Performs actions necessary for the concrete lock. For example, it
     * might be code input or some puzzle solving or nothing special,
     * just applying a key.
     *
     * @param tool Tools to unlock the lock.
     * @return {@code true} if {@code Lock} is opened
     */
    protected abstract boolean unlock(Tool tool);

    /**
     * Tries to unlock {@code this} using {@code tool}
     *
     * @param tool Tools to unlock the lock.
     * @return {@code true} if unlocking is succeed, {@code else} otherwise.
     */
    public final boolean tryUnlock(Tool tool) {
        isUnlocked = isUnlocked || unlock(tool);
        return isUnlocked;
    }

    /**
     * @return List of {@link Tool}s necessary for unlocking {@code this}.
     */
    protected final List<Tool> getTools() {
        return new LinkedList<>(tools);
    }

    /**
     * Adds {@link Tool} necessary for unlocking {@code this}. This might be
     * not only tools that have to be passed in {@link Lock#tryUnlock(Tool)}
     * method, but also those which contains tips (e.g. a pieces of code).
     *
     * @param tool {@link Tool}
     */
    protected final void addTool(Tool tool) {
        tools.add(tool);
    }
}
