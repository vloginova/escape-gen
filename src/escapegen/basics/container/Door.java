package escapegen.basics.container;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.Container;

/**
 * @author - Vita Loginova
 */
public class Door extends Container {

    public Door() {
        super("Door", Size.Large);
        this.lock = new SimpleKeyLock("RubyKey");
    }

    @Override
    public void showContent() { System.out.println("Congrats!"); }

    @Override
    public void examine() {
        System.out.println("Looks tough, don't think I can broke it. But there is a keyhole.");
    }
}
