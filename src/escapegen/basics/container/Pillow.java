package escapegen.basics.container;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Pillow extends Container {
    int count = 3;

    public Pillow() {
        super("Pillow", Size.Medium);
    }

    @Override
    public void showContent() {
        if (count > 0) {
            count--;
            System.out.println("There is nothing here.");
        } else {
            System.out.println("Something fell out of the pillow.");
            Containers.describeContent(items.values());
        }
    }
}
