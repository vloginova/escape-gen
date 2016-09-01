package escapegen.basics.container;

import escapegen.model.*;

/**
 * @author - Vita Loginova
 */
@escapegen.model.Room
@ItemProperty(size = ItemProperties.Size.RoomSize)
public class Room extends AbstractContainer {
    @ViewFor(Room.class)
    @Override
    public void setDescription(ContainerDescription<?> description) {
        super.setDescription(description);
    }
}
