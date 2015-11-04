package escapegen.context;

import escapegen.basics.container.*;
import escapegen.model.Container;
import escapegen.model.Furniture;
import escapegen.model.Item;
import escapegen.model.Tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
public class Generator {

    private List<Class> containerClasses;
    private Class goalClass;

    public Generator() {
        containerClasses = new LinkedList<>();
        containerClasses.add(Table.class);
        containerClasses.add(Vault.class);
        containerClasses.add(Bin.class);
        containerClasses.add(Pillow.class);

        goalClass = Door.class;
    }

    private Container genContainer(Random random)
            throws IllegalAccessException, InstantiationException {
        int index = random.nextInt(containerClasses.size());
        return  (Container) containerClasses.remove(index).newInstance();
    }

    private Item removeRandItem(Random random, List<? extends Item> items) {
        int index = random.nextInt(items.size());
        return items.remove(index);
    }

    private Item getRandItem(Random random, List<? extends Item> items) {
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    private Container popPlaceTo(Random random, Tool tool, List<Container> containers) {
        LinkedList<Container> filtered = containers.stream()
                .filter(t -> !t.dependsOn(tool))
                .collect(Collectors.toCollection(LinkedList::new));

        if (filtered.isEmpty())
            return null;

        Container container = (Container) getRandItem(random, filtered);
        containers.remove(container);
        return container;
    }

    private List<Tool> locateTools(Random random, List<Tool> tools,
                                   List<Container> containers) {
        List<Tool> rest = new LinkedList<>();

        while (!tools.isEmpty() && !containers.isEmpty()) {
            Tool tool = (Tool) removeRandItem(random, tools);
            Container container = popPlaceTo(random, tool, containers);

            if (container == null) {
                rest.add(tool);
            } else {
                container.putItem(tool);
                tools.addAll(container.getLockTools());
            }
        }

        rest.addAll(tools);
        return rest;
    }

    public Game generate(int bound) {
        Game game = new Game();
        Random random = game.getRandom();
        List<Item> furniture = new LinkedList<>();

        try {
            List<Tool> tools = new LinkedList<>();
            List<Container> free = new LinkedList<>();
            List<Container> withTools = new LinkedList<>();

            /* Step 1: Generate goal. */
            Container goal = (Container) goalClass.newInstance();
            tools.addAll(goal.getLockTools());
            furniture.add(goal);
            game.setGoal(goal);

            /* Step 2: Generate containers. */
            while (bound-- > 0 && !containerClasses.isEmpty()) {
                Container container = genContainer(random);
                furniture.add(container);

                List<Container> temp = new LinkedList<>();

                if (Furniture.class.isInstance(container)) {
                    Furniture f = (Furniture) container;
                    temp.addAll(f.getContainers());
                } else {
                    temp.add(container);
                }

                temp.stream().forEach(c -> {
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
            tools.forEach(t -> game.inventory().put(t.toString(), t));

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        game.setCurrentSpace(new Room(furniture));
        return game;
    }
}
