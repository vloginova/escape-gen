package escapegen.basics.tool;

import escapegen.model.*;
import lombok.Setter;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@ItemProperty(size = ItemProperties.Size.Small, shape = ItemProperties.Shape.Flat)
public class InvisibleInkPaper extends Paper {
    @Setter
    private String invisibleText;
    @Setter
    private Lamp lamp;

    @PostConstruct
    private void configure() {
        setText("*blank*");
    }

    @ViewFor(InvisibleInkPaper.class)
    @Override
    public void setDescription(ToolDescription<?> description) {
        super.setDescription(description);
    }

    @Override
    public boolean apply(Item item) {
        if (lamp == item) {
            setText(invisibleText);
            return true;
        }
        return false;
    }
}
