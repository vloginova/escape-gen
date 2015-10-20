package escapegen.basics;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Box extends Container {

    protected Box(String id, Size size) {
        super(id, size);
        this.lock = new SimpleKeyLock("GoldenKey");
    }

    @Override
    protected void showContent() {
        if (items.values().isEmpty()) {
            System.out.println("There is nothing here.");
        } else {
            System.out.println("Let's see...");
            items.values().forEach(System.out::println);
        }
    }
}
