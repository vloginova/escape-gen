package escapegen.model;

/**
 * TODO
 *
 * @author - Vita Loginova
 */
public abstract class Item {

    private final String id;
    private Size size;

    protected Item(String id) {
        this.id = id;
        this.size = Size.Medium;
    }

    public abstract void examine();

    public boolean apply(Item item) {
        return false; /* nothing happened */
    }

    public boolean isVisible() { return true; }

    public enum Size {
        Small, Medium, Large, RoomSize
    }

    public final void setSize(Size size) {
        this.size = size;
    }

    @Override
    public final String toString() {
        return id;
    }
}
