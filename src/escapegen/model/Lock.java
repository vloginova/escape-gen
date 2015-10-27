package escapegen.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Locks cannot be self-independent, they are always a part of some
 * {@link Container}. They are always created being locked. Also locks are
 * associated with its tool (= key) or a set of tools.
 *
 * @author - Vita Loginova
 */
public abstract class Lock {

    protected boolean isUnlocked = false;
    protected Map<String, Tool> tools = new HashMap<>();

    /**
     * Performs actions necessary for the concrete lock. For example, it
     * might be code input or some puzzle solving or nothing special,
     * just applying a key.
     *
     * @return {@code true} if {@code Lock} is opened
     */
    protected abstract boolean unlock(Collection<Tool> bp);

    /**
     * Tries to unlock {@code this} using {@code tools}
     *
     * @param tools Tools to unlock the lock.
     * @return {@code true} if unlocking is succeed, {@code else} otherwise.
     */
    public final boolean tryUnlock(Collection<Tool> tools) {
        isUnlocked = isUnlocked || unlock(tools);
        return isUnlocked;
    }

    /**
     * @return Collection of {@link Tool}s necessary for unlocking {@code this}.
     */
    protected final Collection<Tool> getTools() {
        return tools.values();
    }

    /**
     * Adds {@link Tool} necessary for unlocking {@code this}. This might be
     * not only tools that have to be passed in {@link Lock#tryUnlock(Collection)}
     * method, but also those which contains tips (e.g. a pieces of code).
     *
     * @param tool {@link Tool}
     */
    protected final void addTool(Tool tool) {
        tools.put(tool.toString(), tool);
    }
}
