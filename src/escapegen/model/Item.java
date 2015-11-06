package escapegen.model;

/**
 * TODO
 *
 * @author - Vita Loginova
 */
public abstract class Item {

    private final String id;
    private final Size size;

    protected Item(String id, Size size) {
        this.id = id;
        this.size = size;
    }

    public abstract void examine();

    public boolean isVisible() { return true; }

    public enum Size {
        Small, Medium, Large, RoomSize
    }

    @Override
    public final String toString() {
        return id;
    }
}
