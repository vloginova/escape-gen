package escapegen.basics.container;

import escapegen.basics.lock.CodeLock;
import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Logniva
 */
@RootContainer
@ItemProperty
public class Vault extends AbstractContainer {
    @Autowired
    private CodeLock myLock;

    @ViewFor(Vault.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    private void configure() {
        lock = myLock;
    }
}
