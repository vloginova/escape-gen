package escapegen.basics.tool;

import escapegen.model.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author - Vita Loginova
 */
@ItemProperty(size = ItemProperties.Size.Small, shape = ItemProperties.Shape.Flat)
public class Paper extends Tool {
    @Getter
    @Setter
    private String text;

    @ViewFor(Paper.class)
    @Override
    public void setDescription(ToolDescription<?> description) {
        super.setDescription(description);
    }
}
