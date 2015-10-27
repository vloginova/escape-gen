package escapegen.basics.container;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Bin extends Container {

    public Bin() {
        super("Bin", Size.Medium);
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
