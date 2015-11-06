package escapegen.basics.tool;

import escapegen.model.Item;

/**
 * @author - Vita Loginova
 */
public class InvisibleInkPaper extends Paper {
    String invisibleText;
    Lamp lamp;

    public InvisibleInkPaper(String id, String text, Lamp lamp) {
        super(id, "*blank*");
        this.lamp = lamp;
        invisibleText = text;
    }

    @Override
    public boolean apply(Item item) {
        if (lamp == item) {
            System.out.println(invisibleText);
            return true;
        }
        return false;
    }
}
