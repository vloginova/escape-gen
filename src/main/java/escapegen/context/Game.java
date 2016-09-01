package escapegen.context;

import escapegen.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game represents the whole context for the concrete game.
 *
 * @author - Vita Loginova
 */
public class Game {

    private Map<String, Tool> inventory;
    private Container currentSpace;
    private AbstractContainer goal;
    private boolean isGameOver = false;
    private Random rand;
    private UserIO userIO = UserIO.getInstance();

    public Game() {
        inventory = new HashMap<>();
        rand = new Random();
    }

    public Map<String, Tool> inventory() {
        return inventory;
    }

    public Container currentSpace() {
        return currentSpace;
    }

    public void setCurrentSpace(Container container) {
        currentSpace = container;
    }

    public boolean isOver() {
        return isGameOver;
    }

    public void endGame() {
        isGameOver = true;
    }

    public Random getRandom() { return rand; }

    public AbstractContainer getGoal() {
        return goal;
    }

    public void setGoal(AbstractContainer goal) {
        this.goal = goal;
    }

    public void examineItem(Item i) {
        userIO.writeString(i.getDescription().describeItem(i).toString());
    }

    private boolean unlock(Lock l, Tool t) {
        if (l == null)
            return true;

        LockDescription<?> lockDescription = l.getDescription();
        if (l.isUnlocked())
            return true;

        userIO.writeString(lockDescription.describeBeforeUnlocking(l).toString());
        boolean result = l.tryUnlock(t);
        if (result)
            userIO.writeString(lockDescription.describeUnlockingSucceed(l).toString());
        else
            userIO.writeString(lockDescription.describeUnlockingFailed(l).toString());
        return result;
    }

    public boolean openContainer(Container c, Tool t) {
        return unlock(c.getLock(), t) && c.tryOpen();
    }

    public void showContent(Container c) {
        AbstractContainer container = (AbstractContainer) c;
        ContainerDescription<?> description = container.getDescription();
        if (c.isEmpty())
            userIO.writeString(description.describeEmptyContent(c).toString());
        else
            userIO.writeString( description.describeContent(c).toString());
    }

    public void apply(Item main, Item applied) {
        ItemDescription<?> description = main.getDescription();
        boolean applyResult = main.apply(applied);
        if (applyResult)
            userIO.writeString(description.describeApplySucceed(main, applied).toString());
        else
            userIO.writeString(description.describeApplyFailed(main, applied).toString());
    }
}
