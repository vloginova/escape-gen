package escapegen.text;

import escapegen.model.Item;
import escapegen.model.ToolDescription;
import lombok.Builder;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author - Vita Loginova
 */
public class TextToolDescription extends TextItemDescription implements ToolDescription<String> {
    @Builder
    private TextToolDescription(Function<Item, String> describeItem,
                        BiFunction<Item, Item, String> describeApplyFailed,
                        BiFunction<Item, Item, String> describeApplySucceed) {
        super(describeItem, describeApplyFailed, describeApplySucceed);
    }
}
