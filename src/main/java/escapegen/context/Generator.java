package escapegen.context;

import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generator is the main class that produces the game.
 *
 * <p>Generation algorithm description:
 *
 * <p><b>Step 1: Generate goal.</b><br>
 * Goal is the object after opening which the game is over.
 *
 * <p><b>Step 2: Generate containers.</b><br>
 * The number of containers to generate is constrained by {@code bound}
 * parameter in {@code generate()} function.
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
public class Generator {
    @Autowired
    private ApplicationContext context;

    private List<String> goals;
    private List<String> rootContainers;
    private List<String> rooms;

    private final Random random = new Random();

    @PostConstruct
    public void configure() {
        goals = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(Goal.class)));
        rootContainers = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(Basic.class)));
        rooms = new ArrayList<>(Arrays.asList(context.getBeanNamesForAnnotation(escapegen.model.Room.class)));
    }

    private AbstractContainer genContainer(Random random)
            throws IllegalAccessException, InstantiationException {
        int index = random.nextInt(rootContainers.size());
        return context.getBean(rootContainers.remove(index), AbstractContainer.class);
    }

    private AbstractContainer genGoal(Random random)
            throws IllegalAccessException, InstantiationException {
        int index = random.nextInt(goals.size());
        return context.getBean(goals.remove(index), AbstractContainer.class);
    }

    private AbstractContainer genRoom(Random random)
            throws IllegalAccessException, InstantiationException {
        int index = random.nextInt(rooms.size());
        return context.getBean(rooms.remove(index), AbstractContainer.class);
    }

    private Item removeRandItem(Random random, List<? extends Item> items) {
        int index = random.nextInt(items.size());
        return items.remove(index);
    }

    private Item getRandItem(Random random, List<? extends Item> items) {
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    private AbstractContainer popPlaceTo(Random random, Tool tool, List<AbstractContainer> containers) {
        LinkedList<AbstractContainer> filtered = containers.stream()
                .filter(c -> ItemProperties.isCompatible(tool, c))
                .filter(c -> !c.dependsOn(tool))
                .collect(Collectors.toCollection(LinkedList::new));

        if (filtered.isEmpty())
            return null;

        AbstractContainer container = (AbstractContainer) getRandItem(random, filtered);
        containers.remove(container);
        return container;
    }

    private List<Tool> locateTools(Random random, List<Tool> tools,
                                   List<AbstractContainer> containers) {
        List<Tool> rest = new LinkedList<>();

        while (!tools.isEmpty() && !containers.isEmpty()) {
            Tool tool = (Tool) removeRandItem(random, tools);
            AbstractContainer container = popPlaceTo(random, tool, containers);

            if (container == null) {
                rest.add(tool);
            } else {
                container.putItem(tool);
                System.out.println("Placed " + tool.getItemProperties().getId()
                        + " into " + container.getItemProperties().getId());
                tools.addAll(container.getLockTools());
            }
        }

        rest.addAll(tools);
        return rest;
    }

    public Game generate(Game game, int bound) {
        List<Item> furniture = new LinkedList<>();
        AbstractContainer room;

        try {
            List<Tool> tools = new LinkedList<>();
            List<AbstractContainer> free = new LinkedList<>();
            List<AbstractContainer> withTools = new LinkedList<>();

            /* Step 1: Generate goal. */
            AbstractContainer goal = genGoal(random);
            tools.addAll(goal.getLockTools());
            furniture.add(goal);
            game.setGoal(goal);

            /* Step 2: Generate containers. */
            while (bound-- > 0 && !rootContainers.isEmpty()) {
                AbstractContainer container = genContainer(random);
                furniture.add(container);

                List<AbstractContainer> temp = new LinkedList<>();

                if (Furniture.class.isInstance(container)) {
                    Furniture f = (Furniture) container;
                    temp.addAll(f.getContainers());
                } else {
                    temp.add(container);
                }

                temp.forEach(c -> {
                    if (c.getLockTools().isEmpty()) {
                        free.add(c);
                    } else {
                        withTools.add(c);
                    }
                });
            }

            /* Step 3: Locate tools in locking containers. */
            tools = locateTools(random, tools, withTools);

            /* Step 4: Locate tools in lock-free containers. */
            tools = locateTools(random, tools, free);

            /* Step 5: Locate tools in the inventory. */
            tools.forEach(t -> game.getInventory().put(t.getId(), t));

            room = genRoom(random);
            room.putAllItems(furniture);
            game.setCurrentSpace(room);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return game;
    }
}
