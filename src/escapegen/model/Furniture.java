package escapegen.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Furniture is a {@link Container} which contains its parts (e.g. boxes of
 * the table) and has spaces (e.g. a space under the table) where objects
 * might be placed.
 *
 * @author - Vita Loginova
 */
public abstract class Furniture extends Container {

    private Map<Space, Container> spaces = new HashMap<>();

    protected Furniture(String id, Size size) {
        super(id, size);
    }

    public enum Space {
        Under, On, LeftSide, RightSide, Back
    }

    /**
     * Brings a {@code space} into correlation with {@code} container.
     */
    protected final void putSpace(Space space, Container container) {
        if (spaces.get(space) != null)
            throw new RuntimeException("Space is already in use.");

        container.setParent(this);
        spaces.put(space, container);
    }

    /**
     * @return A {@code container} associated with {@code space}, {@code null}
     * in case of its absence.
     */
    public final Container getSpace(Space space) {
        return spaces.get(space);
    }

    private Collection<Container> filterContainers(Collection<Item> items) {
        return items.stream()
                .filter(Container.class::isInstance)
                .map(item -> (Container) item)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @return List of {@link Container}s, including spaces, parts of the
     * Furniture and containers placed in the spaces.
     */
    public final List<Container> getContainers() {
        List<Container> result = new LinkedList<>();

        result.addAll(spaces.values());
        result.addAll(filterContainers(items.values()));

        for (Container space : spaces.values()) {
            result.addAll(filterContainers(space.items.values()));
        }

        return result;
    }
}
