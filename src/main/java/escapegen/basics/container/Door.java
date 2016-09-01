package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@Goal
@ItemProperty(size = ItemProperties.Size.Large)
public class Door extends AbstractContainer {
    @Autowired
    SimpleKeyLock myLock;

    @ViewFor(Door.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    private void configure() {
        setLock(myLock);
    }
}
