package escapegen.basics.tool;

import escapegen.model.*;

/**
 * @author - Vita Loginova
 */
@ItemProperty(size = ItemProperties.Size.Small, matter = ItemProperties.Matter.Hard)
public class Lamp extends Tool {
    @ViewFor(Lamp.class)
    @Override
    public void setDescription(ToolDescription<?> description) {
        super.setDescription(description);
    }

    @Override
    public boolean apply(Item item) {
        return item.apply(this);
    }
}
