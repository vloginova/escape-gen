package escapegen.text;

import escapegen.model.Container;
import escapegen.model.ContainerDescription;
import escapegen.model.Item;
import lombok.Builder;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author - Vita Loginova
 */
public class TextContainerDescription extends TextItemDescription implements ContainerDescription<String> {
    private Function<Container, String> describeEmptyContent = c -> "There is nothing here.";
    private Function<Container, String> describeContent = TextContainerDescription::describeContentDefault;

    @Builder
    private TextContainerDescription(Function<Item, String> describeItem,
                                     BiFunction<Item, Item, String> describeApplyFailed,
                                     BiFunction<Item, Item, String> describeApplySucceed,
                                     Function<Container, String> describeEmptyContent,
                                     Function<Container, String> describeContent) {
        super(describeItem, describeApplyFailed, describeApplySucceed);
        if (describeEmptyContent != null)
            this.describeEmptyContent = describeEmptyContent;
        if (describeContent != null)
            this.describeContent = describeContent;
    }

    @Override
    public String describeEmptyContent(Container c) {
        return describeEmptyContent.apply(c);
    }

    @Override
    public String describeContent(Container c) {
        return describeContent.apply(c);
    }

    private static String describeContentDefault(Container c) {
        return c.getItems().stream()
                .map(Item::getId)
                .collect(Collectors.joining(", "));
    }
}
