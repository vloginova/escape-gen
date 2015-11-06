package escapegen.basics.container;

import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Bin extends Container {

    public Bin() {
        super("Bin");
    }

    @Override
    public void showContent() {
        System.out.println("A couple of useless creased pieces of paper.");
        if (!items.values().isEmpty()) {
            System.out.println("Oh, look at it!");
            Containers.describeContent(items.values());
        }
    }

    @Override
    public void examine() {
        System.out.println("A black plastic recycle bin.");
    }
}
