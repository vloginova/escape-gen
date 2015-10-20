package escapegen.basics;

import escapegen.model.Container;

/**
 * @author - Vita Logniva
 */
public class Vault extends Container {

    public Vault() {
        super("Vault", Size.Medium);
        this.lock = new CodeLock();
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
