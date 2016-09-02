package escapegen.context;

import escapegen.context.configuration.GameConfig;
import escapegen.model.AbstractContainer;
import escapegen.model.Basic;
import escapegen.model.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Generator is the main class that produces the game.
 *
 * <p>Generation algorithm description:
 *
 * <p><b>Step 1: Generate goal.</b><br>
 * Goal is the object after opening which the game is over.
 *
 * <p><b>Step 2: Generate containers.</b><br>
 * The number of containers to loadGame is constrained by {@code bound}
 * parameter in {@code loadGame()} function.
 *
 * <p><b>Step 3: Locate tools in locking containers.</b><br>
 * To open locking container it is needed to apply some tools. Such restriction
 * helps to avoid unexpected shortcuts in the game.
 *
 * <p><b>Step 4: Locate tools in lock-free containers.</b><br>
 * The rest tools are put into containers that don't need tools to open them.
 * But still that containers can have locks, e.g. puzzle.
 *
 * <p><b>Step 5: Locate tools in the inventory.</b><br>
 * If some tools are left, they are put in the inventory. So that there is
 * always a way to reach the goal.
 *
 * @author - Vita Loginova
 */
@Component
public class Generator extends GameLoaderImpl {
    @Autowired
    private ApplicationContext context;

    private GameConfig config;

    private List<String> goals;
    private List<String> rootContainers;
    private List<String> rooms;

    @PostConstruct
    public void configure() {
        if (config != null) {
            goals = Collections.singletonList(config.getGoalBeanClassName());
            rooms = Collections.singletonList(config.getRoomBeanClassName());
            rootContainers = new LinkedList<>(config.getBasicsBeanClassNames());
        } else {
            goals = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(Goal.class)));
            rootContainers = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(Basic.class)));
            rooms = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(escapegen.model.Room.class)));
        }
    }

    @Override
    protected AbstractContainer genContainer(Random random)  {
        int index = random.nextInt(rootContainers.size());
        return context.getBean(rootContainers.remove(index), AbstractContainer.class);
    }

    @Override
    protected AbstractContainer genGoal(Random random) {
        int index = random.nextInt(goals.size());
        return context.getBean(goals.remove(index), AbstractContainer.class);
    }

    @Override
    protected AbstractContainer genRoom(Random random) {
        int index = random.nextInt(rooms.size());
        return context.getBean(rooms.remove(index), AbstractContainer.class);
    }

    @Override
    protected boolean isContainersEmpty() {
        return rootContainers.isEmpty();
    }
}
