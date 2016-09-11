package escapegen.basics.container;

import escapegen.model.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@Basic
@ItemProperty(size = ItemProperties.Size.Large)
public class Bed extends Furniture {

    @Autowired
    protected Pillow pillow;
    @Autowired
    protected OnBed onBed;
    @Autowired
    protected UnderBed underBed;

    @ViewFor(Bed.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    private void configure() {
        onBed.putItem(pillow);
        pillow.setOnBed(onBed);
        this.putSpace(Space.ON, onBed);
        this.putSpace(Space.UNDER, underBed);
    }

    @Reusable
    @ItemProperty(size = ItemProperties.Size.Small, matter = ItemProperties.Matter.Soft)
    public static class OnBed extends AbstractContainer {
        @ViewFor(OnBed.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }
    }

    @Reusable
    @ItemProperty
    public static class UnderBed extends AbstractContainer {
        @ViewFor(UnderBed.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }
    }

    @Reusable
    @ItemProperty(matter = ItemProperties.Matter.Soft)
    public static class Pillow extends AbstractContainer {
        private int count = 3;
        @Setter
        private OnBed onBed;

        @ViewFor(Pillow.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }

        @Override
        public void putItem(Item item) {
            item.setVisible(false);
            super.putItem(item);
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean tryOpen() {
            if (count > 0) count--;
            if (count == 0) {
                items.values().forEach(i -> i.setVisible(true));
                onBed.putAllItems(removeItems());
            }
            return count > 0 || super.tryOpen();
        }
    }
}
