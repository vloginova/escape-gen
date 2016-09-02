package escapegen.context;

import escapegen.context.configuration.GameConfig;
import escapegen.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Game represents the whole context for the concrete game.
 *
 * @author - Vita Loginova
 */
@Component
@Scope
public class Game {

    @Getter @Setter
    private Map<String, Tool> inventory = new HashMap<>();
    @Getter @Setter
    private Container currentSpace;
    @Getter @Setter
    private Container room;
    @Getter @Setter
    private Container goal;
    @Getter @Setter
    private boolean isGameOver = false;
    @Getter
    @Resource(name = "userIOWeb")
    private UserIO<String> userIO;
    @Autowired
    private Generator generator;
    @Autowired
    private ConfigLoader configLoader;

    @PostConstruct
    private void init() {
        generator.loadGame(this, 5);
    }

    public void examineItem(Item i) {
        userIO.write(i.getDescription().describeItem(i).toString());
    }

    private boolean unlock(Lock l, Tool t) {
        if (l == null)
            return true;

        LockDescription<?> lockDescription = l.getDescription();
        if (l.isUnlocked())
            return true;

        userIO.write(lockDescription.describeBeforeUnlocking(l).toString());
        boolean result = l.tryUnlock(t);
        if (result)
            userIO.write(lockDescription.describeUnlockingSucceed(l).toString());
        else
            userIO.write(lockDescription.describeUnlockingFailed(l).toString());
        return result;
    }

    public boolean openContainer(Container c, Tool t) {
        return unlock(c.getLock(), t) && c.tryOpen();
    }

    public void showContent(Container c) {
        AbstractContainer container = (AbstractContainer) c;
        ContainerDescription<?> description = container.getDescription();
        if (c.isEmpty())
            userIO.write(description.describeEmptyContent(c).toString());
        else
            userIO.write(description.describeContent(c).toString());
    }

    public void apply(Item main, Item applied) {
        ItemDescription<?> description = main.getDescription();
        boolean applyResult = main.apply(applied);
        if (applyResult)
            userIO.write(description.describeApplySucceed(main, applied).toString());
        else
            userIO.write(description.describeApplyFailed(main, applied).toString());
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
                .map(i -> i.getClass().getName())
                .collect(Collectors.toList()));
        return config;
    }
}
