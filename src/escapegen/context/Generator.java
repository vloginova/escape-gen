package escapegen.context;

import escapegen.basics.*;
import escapegen.model.Container;
import escapegen.model.Item;
import escapegen.model.Tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
            tools.addAll(goal.getTools());
            furniture.add(goal);
            game.setGoal(goal);

            /* Step 2: Generate containers. */
            while (bound-- > 0 && !containerClasses.isEmpty()) {
                Container container = genContainer(random);
                furniture.add(container);

                List<Container> temp = new LinkedList<>(container.getContainers());
                temp.add(container);

                temp.stream().filter(c -> !c.isFull()).forEach(c -> {
                    if (c.getTools().isEmpty()) {
                        free.add(c);
                    } else {
                        withTools.add(c);
                    }
                });
            }

            /* Step 3: Locate tools in locking containers. */
            while (!tools.isEmpty() && !withTools.isEmpty()) {
                Tool tool = (Tool) removeRandItem(random, tools);
                Container container = (Container) removeRandItem(random, withTools);
                container.putItem(tool);
                tools.addAll(container.getTools());
            }

            /* Step 4: Locate tools in lock-free containers. */
            while (!tools.isEmpty() && !free.isEmpty()) {
                Tool tool = (Tool) removeRandItem(random, tools);
                Container container = (Container) removeRandItem(random, free);
                container.putItem(tool);
            }

            /* Step 5: Locate tools in the inventory. */
            tools.forEach(t -> game.inventory().put(t.toString(), t));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        game.setCurrentSpace(new Room(furniture));
        return game;
    }
}
