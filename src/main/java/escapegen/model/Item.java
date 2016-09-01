package escapegen.model;

/**
 * TODO
 *
 * @author - Vita Loginova
 */
public interface Item {
    String getId();
    ItemDescription<?> getDescription();
    ItemProperties getItemProperties();
    void setItemProperties(ItemProperties properties);

    boolean isVisible();
    void setVisible(boolean isVisible);
    boolean apply(Item i);
}
