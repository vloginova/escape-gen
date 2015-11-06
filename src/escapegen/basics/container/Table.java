package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.Container;
import escapegen.model.Furniture;

/**
 * @author - Vita Loginova
 */
public class Table extends Furniture {

    public Table() {
        super("Table");

        Box topBox = new Box("TopBox");
        Box secretBox = new Box("SecretBox") {
            @Override
            public boolean isVisible() {
                return topBox.isOpened();
            }
        };

        secretBox.setSize(Size.Small);
        topBox.setSize(Size.Small);

        secretBox.addDependencies(topBox);

        Container back = new Container("TableBack") {
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

        back.setSize(Size.Small);
        back.putItem(secretBox);
        this.putSpace(Space.Back, back);

        Container on = new Container("On Table") {
            @Override
            public void examine() {
                System.out.println("Clean, contains only useful stuff.");
            }

            @Override
            public void showContent() {
                Containers.describeContent(items.values());
            }
        };

        on.setMatter(Matter.Hard);
        this.putSpace(Space.On, on);

        this.putItem(topBox);
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

        protected Box(String id) {
            super(id);
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
