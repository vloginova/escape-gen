package escapegen.basics.container;

import escapegen.model.Item;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Useful utility functions for managing {@link Containers} class.
 *
 * @author - Vita Loginova
 */
public final class Containers {
    private Containers() { }

    public static void describeContent(Collection<Item> items) {
        if (items.isEmpty()) {
            System.out.println("There is nothing here.");
        } else {
            System.out.println("Let's see...");
            System.out.println(items.stream()
                    .filter(Item::isVisible)
                    .map(Item::toString)
                    .collect(Collectors.joining(", ")));
        }
    }
}
