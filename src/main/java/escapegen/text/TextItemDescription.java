package escapegen.text;

import escapegen.model.Item;
import escapegen.model.ItemDescription;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author - Vita Loginova
 */
public abstract class TextItemDescription implements ItemDescription<String> {
    protected Function<Item, String> describeItem;
    protected BiFunction<Item, Item, String> describeApplyFailed = (i, applied) -> "Nothing happened";
    protected BiFunction<Item, Item, String> describeApplySucceed = (i, applied) -> "Nothing happened";

    TextItemDescription(Function<Item, String> describeItem,
                        BiFunction<Item, Item, String> describeApplyFailed,
                        BiFunction<Item, Item, String> describeApplySucceed) {
        if (describeItem == null)
            throw new NullPointerException("Item description cannot be null");

        this.describeItem = describeItem;
        if (describeApplyFailed != null)
            this.describeApplyFailed = describeApplyFailed;
        if (describeApplySucceed != null)
            this.describeApplySucceed = describeApplySucceed;
    }

    @Override
    public String describeItem(Item i) {
        return describeItem.apply(i);
    }

    @Override
    public String describeApplyFailed(Item i, Item applied) {
        return describeApplyFailed.apply(i, applied);
    }

    @Override
    public String describeApplySucceed(Item i, Item applied) {
        return describeApplySucceed.apply(i, applied);
    }
}
