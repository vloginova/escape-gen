package escapegen.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Furniture is a {@link AbstractContainer} which contains its parts (e.g. boxes of
 * the table) and has spaces (e.g. a space under the table) where objects
 * might be placed.
 *
 * @author - Vita Loginova
 */
public abstract class Furniture extends AbstractContainer {

    private Map<Space, AbstractContainer> spaces = new HashMap<>();

    public enum Space {
        Under, On, LeftSide, RightSide, Back
    }

    /**
     * Brings a {@code space} into correlation with {@code} container.
     */
    protected final void putSpace(Space space, AbstractContainer container) {
        if (spaces.get(space) != null)
            throw new RuntimeException("Space is already in use.");

        container.setParent(this);
        spaces.put(space, container);
    }

    /**
     * @return A {@code container} associated with {@code space}, {@code null}
     * in case of its absence.
     */
    public final AbstractContainer getSpace(Space space) {
        return spaces.get(space);
    }

    private Collection<AbstractContainer> filterContainers(Collection<Item> items) {
        return items.stream()
                .filter(AbstractContainer.class::isInstance)
                .map(item -> (AbstractContainer) item)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @return List of {@link AbstractContainer}s, including spaces, parts of the
     * Furniture and containers placed in the spaces.
     */
    @SuppressWarnings("unchecked")
    public final List<AbstractContainer> getContainers() {
        List<AbstractContainer> result = new LinkedList<>();

        result.addAll(spaces.values());
        result.addAll(filterContainers(items.values()));

        for (AbstractContainer space : spaces.values()) {
            result.addAll(filterContainers(space.items.values()));
        }

        return result;
    }
}
