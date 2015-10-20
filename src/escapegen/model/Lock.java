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
    protected Map<String, Tool> tools = new HashMap<>(3);

    /**
     * Performs actions necessary for the concrete lock. For example, it
     * might be code input or some puzzle solving or nothing special,
     * just applying a key.
     *
     * @return {@code true} if {@code Lock} is opened
     */
    protected abstract boolean unlock(Collection<Tool> bp);

    /**
     * Checks if {@code Lock} is already opened.
     *
     * @return {@code true} if {@code Lock} is opened
     */
    public boolean isUnlocked() {
        return isUnlocked;
    }

    public boolean tryUnlock(Collection<Tool> bp) {
        isUnlocked = isUnlocked || unlock(bp);
        return isUnlocked;
    }

    public Map<String, Tool> getTools() {
        return tools;
    }

    protected void putTool(Tool tool) {
        tools.put(tool.toString(), tool);
    }
}
