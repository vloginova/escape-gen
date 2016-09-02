package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@Basic
@ItemProperty
public class Table extends Furniture {

    @Autowired
    private Box topBox;
    @Autowired
    private SecretBox secretBox;
    @Autowired
    private TableBack tableBack;
    @Autowired
    private OnTable onTable;

    @PostConstruct
    private void configure() {
        secretBox.addDependency(topBox);
        secretBox.setTopBox(topBox);
        tableBack.putItem(secretBox);

        putSpace(Space.On, onTable);
        putSpace(Space.Back, tableBack);
        putItem(topBox);
    }

    @ViewFor(Table.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }

    @Reusable
    @ItemProperty(size = ItemProperties.Size.Small)
    public static class Box extends AbstractContainer {
        @Autowired
        SimpleKeyLock myLock;

        @ViewFor(Box.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }

        @PostConstruct
        private void configure() {
            setLock(myLock);
        }
    }

    @Reusable
    @ItemProperty(size = ItemProperties.Size.Small)
    public static class SecretBox extends AbstractContainer {
        @Autowired
        SimpleKeyLock myLock;
        @Setter
        private Box topBox;


        @ViewFor(SecretBox.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }

        @PostConstruct
        private void configure() {
            setLock(myLock);
        }

        @Override
        public boolean isVisible() {
            return super.isVisible() && topBox.isOpened();
        }
    }

    @Reusable
    @ItemProperty(size = ItemProperties.Size.Small)
    public static class TableBack extends AbstractContainer {
        @ViewFor(TableBack.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }
    }

    @Reusable
    @ItemProperty(matter = ItemProperties.Matter.Hard)
    public static class OnTable extends AbstractContainer {
        @ViewFor(OnTable.class)
        @Override
        public void setDescription(ContainerDescription<?> description) {
            super.setDescription(description);
        }
    }
}
