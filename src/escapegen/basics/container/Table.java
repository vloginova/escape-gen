package escapegen.basics.container;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Table extends Container {

    public Table() {
        super("Table", Size.Medium);

        Box topBox = new Box("TopBox", Size.Small);
        Box secretBox = new Box("SecretBox", Size.Small);

        this.putItem(topBox);
        this.putItem(secretBox);
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
