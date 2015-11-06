package escapegen.basics.container;

import escapegen.model.Container;
import escapegen.model.Furniture;

/**
 * @author - Vita Loginova
 */
public class Bed extends Furniture {
    public Bed() {
        super("Bed", Size.Large);

        Container on = new Container("On bed", Size.Small) {
            @Override
            public void examine() {
                System.out.println("Simple blue and white bed linen.");
            }

            @Override
            public void showContent() {
                examine();
                Containers.describeContent(items.values());
            }
        };

        on.putItem(new Pillow());

        this.putSpace(Space.On, on);
        this.putSpace(Space.Under, new Container("Under bed", Size.Medium) {
            @Override
            public void examine() {
                System.out.println("Dark but clean.");
            }

            @Override
            public void showContent() {
                Containers.describeContent(items.values());
            }
        });
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }

    @Override
    public void examine() {
        System.out.println("A quite small made up twin-size bed.");
    }

    private class Pillow extends Container {
        int count = 3;

        public Pillow() {
            super("Pillow", Size.Medium);
        }

        @Override
        public void showContent() {
            if (count > 0) {
                if (--count == 0) {
                    System.out.println("Something fell out of the pillow on a bed!");
                    this.getParent().putAllItems(items.values());
                    items.values().clear();
                    return;
                }
            }

            System.out.println("There is nothing here.");
        }

        @Override
        public void examine() {
            System.out.println("Just a soft pillow. *Yawn*");
        }
    }
}
