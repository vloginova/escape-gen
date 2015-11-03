package escapegen.basics.container;

import escapegen.model.Container;
import escapegen.model.Furniture;

/**
 * @author - Vita Loginova
 */
public class Table extends Furniture {

    public Table() {
        super("Table", Size.Medium);

        Box topBox = new Box("TopBox", Size.Small);
        Box secretBox = new Box("SecretBox", Size.Small) {
            @Override
            public boolean isVisible() {
                return topBox.isOpened();
            }
        };

        Container back = new Container("TableBack", Size.Small) {
            @Override
            public void showContent() {
                System.out.println("Dark and a little bit dusty.");
                Containers.describeContent(items.values());
            }
        };

        this.putItem(topBox);

        this.putSpace(Space.Back, back);
        back.putItem(secretBox);
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }
}
