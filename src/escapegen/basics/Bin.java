package escapegen.basics;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Bin extends Container {

    public Bin() {
        super("Bin", Size.Medium);
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
