package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Box extends Container {

    protected Box(String id, Size size) {
        super(id, size);
        this.lock = new SimpleKeyLock(id + "Key");
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
