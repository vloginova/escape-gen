package escapegen.context;

import escapegen.context.configuration.GameConfig;
import escapegen.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Game represents the whole context for the concrete game.
 *
 * @author - Vita Loginova
 */
@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Game {
    private Container wfiContainer;

    public enum State {
        WAITING_FOR_COMMAND, WAITING_FOR_INPUT, GAME_OVER;
    }
    @Getter @Setter
    private Map<String, Tool> inventory = new HashMap<>();

    @Getter
    private Container currentSpace;
    @Getter
    private Container room;
    @Getter @Setter
    private Container goal;
    @Getter @Setter
    private Game.State state = State.WAITING_FOR_COMMAND;
    @Getter
    @Resource(name = "userPrinterWeb")
    private UserPrinter<String> userPrinter;
    @Autowired
    private Generator generator;
    @Autowired
    private ConfigLoader configLoader;
    private boolean loaded;

    private Function<String, UnlockingResult> callback;
    private void clear() {
        inventory.clear();
        currentSpace = null;
        room = null;
        goal = null;
        state = State.WAITING_FOR_COMMAND;
        loaded = false;
    }

    public void loadGame(GameConfig config) {
        clear();
        if (loaded)
            throw new IllegalStateException("Game cannot be loaded twice");
        configLoader.configure(config);
        configLoader.loadGame(this, Integer.MAX_VALUE);
        loaded = true;
    }

    public void loadGame() {
        clear();
        if (loaded)
            throw new IllegalStateException("Game cannot be loaded twice");
        generator.configure();
        int containers = 10;
        generator.loadGame(this, containers);
        loaded = true;
    }

    public boolean isGameOver() {
        return state == State.GAME_OVER;
    }

    public void setRoom(Container room) {
        this.room = room;
        currentSpace = room;
    }

    public void examineItem(Item i) {
        userPrinter.println(i.getDescription().describeItem(i).toString());
    }

    private boolean processUnlockingResult(Lock l, UnlockingResult result) {
        LockDescription<?> lockDescription = l.getDescription();
        if (result.isUnlocked()) {
            userPrinter.println(lockDescription.describeUnlockingSucceed(l).toString());
            setState(State.WAITING_FOR_COMMAND);
        } else if (result.isIntermediate()) {
            this.callback = result.getCallback();
            setState(State.WAITING_FOR_INPUT);
        } else {
            userPrinter.println(lockDescription.describeUnlockingFailed(l).toString());
            setState(State.WAITING_FOR_COMMAND);
        }
        return result.isUnlocked();
    }

    private boolean unlock(Lock l, Tool t) {
        if (l == null || l.isUnlocked())
            return true;

        LockDescription<?> lockDescription = l.getDescription();
        userPrinter.println(lockDescription.describeBeforeUnlocking(l).toString());
        return processUnlockingResult(l, l.tryUnlock(t));
    }

    private boolean unlockIntermediate(Lock l, String input) {
        return processUnlockingResult(l, callback.apply(input));
    }

    public boolean processInput(String input) {
        return openContainerIntermediate(wfiContainer, input);
    }

    public void processOpenContainerResult(Container c, boolean result) {
        if (result) {
            currentSpace = c;
            showContent(c);
        } else if (state == State.WAITING_FOR_INPUT) {
            wfiContainer = c;
        }
    }

    public boolean openContainerIntermediate(Container c, String input) {
        boolean result = unlockIntermediate(c.getLock(), input) && c.tryOpen();
        processOpenContainerResult(c, result);
        return result;
    }

    public boolean openContainer(Container c, Tool t) {
        boolean result = unlock(c.getLock(), t) && c.tryOpen();
        processOpenContainerResult(c, result);
        return result;
    }

    public void showContent(Container c) {
        AbstractContainer container = (AbstractContainer) c;
        ContainerDescription<?> description = container.getDescription();
        if (c.isEmpty()) {
            userPrinter.println(description.describeEmptyContent(c).toString());
        } else {
            userPrinter.println(description.describeContent(c).toString());
        }
    }

    public void apply(Item main, Item applied) {
        ItemDescription<?> description = main.getDescription();
        boolean applyResult = main.apply(applied);
        if (applyResult)
            userPrinter.println(description.describeApplySucceed(main, applied).toString());
        else
            userPrinter.println(description.describeApplyFailed(main, applied).toString());
    }

    public boolean look(Furniture f, Furniture.Space space) {
        Container c = f.getSpace(space);
        if (c == null)
            return false;

        currentSpace = c;
        showContent(c);
        return true;
    }

    public void back() {
        currentSpace = currentSpace.getParent();
    }

    public String getPathToCurrentLocation() {
        StringBuilder builder = new StringBuilder();
        Container container = getCurrentSpace();
        builder.append(container.getId());
        while (container.getParent() != container) {
            container = container.getParent();
            builder.insert(0, "/");
            builder.insert(0, container.getId());
        }
        return builder.toString();
    }

    public GameConfig getConfiguration() {
        GameConfig config = new GameConfig();
        config.setRoomBeanClassName(getRoom().getClass().getName());
        config.setGoalBeanClassName(getGoal().getClass().getName());
        config.setBasicsBeanClassNames(room.getItems().stream()
                .filter(item -> item != getGoal())
                .map(i -> i.getClass().getName())
                .collect(Collectors.toList()));
        return config;
    }
}
