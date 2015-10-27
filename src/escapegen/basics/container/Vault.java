package escapegen.basics.container;

import escapegen.basics.lock.CodeLock;
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
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
