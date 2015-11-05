package escapegen.basics.container;

import escapegen.model.Container;
import escapegen.model.Furniture;

/**
 * @author - Vita Loginova
 */
public class Bed extends Furniture {
    public Bed() {
        super("Bed", Size.Large);

        Container on = new Container("OnBed", Size.Small) {
            @Override
            public void showContent() {
                Containers.describeContent(items.values());
            }
        };

        on.putItem(new Pillow());

        this.putSpace(Space.On, on);
        this.putSpace(Space.Under, new Container("UnderBed", Size.Medium) {
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
}
