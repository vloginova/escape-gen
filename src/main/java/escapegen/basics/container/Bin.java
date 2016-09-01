package escapegen.basics.container;

import escapegen.model.*;

/**
 * @author - Vita Loginova
 */
@RootContainer
@ItemProperty
public class Bin extends AbstractContainer {
    @ViewFor(Bin.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }
}
