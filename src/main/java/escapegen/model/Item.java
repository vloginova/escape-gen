package escapegen.model;

/**
 * TODO
 *
 * @author - Vita Loginova
 */
public interface Item {
    ItemDescription<?> getDescription();
    ItemProperties getItemProperties();

    boolean isVisible();
    void setVisible(boolean isVisible);
    boolean apply(Item i);
}
