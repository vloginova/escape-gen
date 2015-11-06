package escapegen.basics.container;

import escapegen.basics.lock.CodeLock;
import escapegen.model.Container;

/**
 * @author - Vita Logniva
 */
public class Vault extends Container {

    public Vault() {
        super("Vault");
        this.lock = new CodeLock();
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }

    @Override
    public void examine() {
        System.out.println("A solid safe with a number lock.");
    }
}
