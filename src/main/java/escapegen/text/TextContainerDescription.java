package escapegen.text;

import escapegen.model.Container;
import escapegen.model.ContainerDescription;
import escapegen.model.Item;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author - Vita Loginova
 */
public class TextContainerDescription extends TextItemDescription implements ContainerDescription<String> {
    private Function<Container, String> describeEmptyContent = c -> "There is nothing here.";
    private Function<Container, String> describeContent = TextContainerDescription::describeContentDefault;

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

    public static TextContainerDescriptionBuilder builder() {
        return new TextContainerDescriptionBuilder();
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

    public static class TextContainerDescriptionBuilder {
        private Function<Item, String> describeItem;
        private BiFunction<Item, Item, String> describeApplyFailed;
        private BiFunction<Item, Item, String> describeApplySucceed;
        private Function<Container, String> describeEmptyContent;
        private Function<Container, String> describeContent;

        TextContainerDescriptionBuilder() {
        }

        public TextContainerDescription.TextContainerDescriptionBuilder describeItem(Function<Item, String> describeItem) {
            this.describeItem = describeItem;
            return this;
        }

        public TextContainerDescription.TextContainerDescriptionBuilder describeApplyFailed(BiFunction<Item, Item, String> describeApplyFailed) {
            this.describeApplyFailed = describeApplyFailed;
            return this;
        }

        public TextContainerDescription.TextContainerDescriptionBuilder describeApplySucceed(BiFunction<Item, Item, String> describeApplySucceed) {
            this.describeApplySucceed = describeApplySucceed;
            return this;
        }

        public TextContainerDescription.TextContainerDescriptionBuilder describeEmptyContent(Function<Container, String> describeEmptyContent) {
            this.describeEmptyContent = describeEmptyContent;
            return this;
        }

        public TextContainerDescription.TextContainerDescriptionBuilder describeContent(Function<Container, String> describeContent) {
            this.describeContent = describeContent;
            return this;
        }

        public TextContainerDescription build() {
            return new TextContainerDescription(describeItem, describeApplyFailed, describeApplySucceed, describeEmptyContent, describeContent);
        }

        public String toString() {
            return "escapegen.text.TextContainerDescription.TextContainerDescriptionBuilder(describeItem=" + this.describeItem + ", describeApplyFailed=" + this.describeApplyFailed + ", describeApplySucceed=" + this.describeApplySucceed + ", describeEmptyContent=" + this.describeEmptyContent + ", describeContent=" + this.describeContent + ")";
        }
    }
}
