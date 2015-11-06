package escapegen.basics.container;

import escapegen.basics.lock.CodePadlock;
import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Trunk extends Container {
    public Trunk() {
        super("Trunk");
        lock = new CodePadlock();
    }

    @Override
    public void showContent() {
        Containers.describeContent(items.values());
    }

    @Override
    public void examine() {
        System.out.println("Very old trunk.");
    }
}
