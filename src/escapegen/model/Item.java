package escapegen.model;

/**
 * TODO
 *
 * @author - Vita Loginova
 */
public abstract class Item {

    private final String id;

    private Size size;
    private Matter matter;
    private Form form;

    protected Item(String id) {
        this.id = id;

        size = Size.Medium;
        matter = Matter.Hard;
        form = Form.General;
    }

    public abstract void examine();

    public boolean apply(Item item) {
        return false; /* nothing happened */
    }

    public boolean isVisible() { return true; }

    public enum Size {
        Small, Medium, Large, RoomSize;

        private static boolean isCompatible(Size e1, Size e2) {
            return e1.ordinal() <= e2.ordinal();
        }
    }

    public enum Matter {
        Hard, Soft;

        private static boolean isCompatible(Matter e1, Matter e2) {
            return e1 == e2;
        }
    }

    public enum Form {
        Flat, Thin, General;
        private static boolean isCompatible(Form e1, Form e2) {
            return e1 == e2 || e2 == General;
        }

    }

    public final void setSize(Size size) {
        this.size = size;
    }

    public final void setForm(Form form) {
        this.form = form;
    }

    public final void setMatter(Matter matter) {
        this.matter = matter;
    }

    public static boolean isCompatible(Item inner, Item outer) {
        return Size.isCompatible(inner.size, outer.size) &&
                Matter.isCompatible(inner.matter, outer.matter) &&
                Form.isCompatible(inner.form, outer.form);
    }

    @Override
    public final String toString() {
        return id;
    }
}
