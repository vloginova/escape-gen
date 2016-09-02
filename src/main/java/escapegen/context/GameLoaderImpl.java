package escapegen.context;

import escapegen.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by vita on 9/2/16.
 */
public abstract class GameLoaderImpl implements GameLoader {
    private Random random = new Random();

    protected abstract AbstractContainer genContainer(Random random);

    protected abstract AbstractContainer genGoal(Random random);

    protected abstract AbstractContainer genRoom(Random random);

    protected abstract boolean isContainersEmpty();

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

    public void loadGame(Game game, int bound) {
        List<Item> furniture = new LinkedList<>();
        AbstractContainer room;

        List<Tool> tools = new LinkedList<>();
        List<AbstractContainer> free = new LinkedList<>();
        List<AbstractContainer> withTools = new LinkedList<>();

            /* Step 1: Generate goal. */
        AbstractContainer goal = genGoal(random);
        tools.addAll(goal.getLockTools());
        furniture.add(goal);
        game.setGoal(goal);

            /* Step 2: Generate containers. */
        while (bound-- > 0 && !isContainersEmpty()) {
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
        game.setRoom(room);
    }
}
