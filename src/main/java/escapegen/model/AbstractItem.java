package escapegen.model;

/**
 * @author - Vita Loginova
 */
public abstract class AbstractItem implements Item {

    private ItemProperties itemProperties;
    private boolean isVisible = true;

    @Override
    public ItemProperties getItemProperties() {
        return itemProperties;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public boolean apply(Item i) {
        return false;
    }
}
