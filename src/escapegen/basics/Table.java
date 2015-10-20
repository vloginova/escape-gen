package escapegen.basics;

import escapegen.model.Container;
import escapegen.model.Item;

/**
 * @author - Vita Loginova
 */
public class Table extends Container {

    public Table() {
        super("Table", Size.Medium);

        Box topBox = new Box("TopBox", Size.Small);
//        Box bottomBox = new Box("BottomBox", Size.Small);

        this.putItem(topBox);
//        this.putItem(bottomBox);
    }

    @Override
    protected void showContent() {
        System.out.println("Let's see...");
        for (Item i : items.values()) {
            System.out.println(i.toString());
        }
    }

    @Override
    public boolean isFull() {
        return true;
    }
}
