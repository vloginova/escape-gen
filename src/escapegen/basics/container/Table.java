package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
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
        secretBox.addDependencies(topBox);

        Container back = new Container("TableBack", Size.Small) {
            @Override
            public void examine() {
                System.out.println("Dark and a little bit dusty.");
            }

            @Override
            public void showContent() {
                examine();
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

    @Override
    public void examine() {
        System.out.println("A big wooden table with one box. It's simple but looks comfortable.");
    }

    public class Box extends Container {

        protected Box(String id, Size size) {
            super(id, size);
            this.lock = new SimpleKeyLock(id + "Key");
        }

        @Override
        public void examine() {
            System.out.println("Wooden box with a little keyhole.");
        }

        @Override
        public void showContent() {
            Containers.describeContent(items.values());
        }
    }

}
