package escapegen.model;

/**
 * Tools are basic object that primarily used for unlocking different containers.
 * For example, this might be a key or a part of some code or a hammer.
 *
 * @author Vita Loginova
 */
public abstract class Tool extends Item {

    private boolean isUsed = false;

    protected Tool(String id, Size size) {
        super(id, size);
    }

    public final boolean isUsed() {
        return isUsed;
    }
    public final void use() {
        isUsed = true;
    }
}
