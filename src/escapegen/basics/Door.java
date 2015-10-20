package escapegen.basics;

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
    protected void showContent() { System.out.println("Congrats!"); }
}
