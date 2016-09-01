package escapegen.basics.container;

import escapegen.basics.lock.CodePadlock;
import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@Basic
@ItemProperty
public class Trunk extends AbstractContainer {
    @Autowired
    private CodePadlock myLock;

    @ViewFor(Trunk.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    private void configure() {
        lock = myLock;
    }
}
