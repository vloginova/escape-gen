package escapegen.text.container;

import escapegen.basics.container.Table;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextTable {
    @Bean
    @ViewFor(Table.class)
    public ContainerDescription<String> tableDescription() {
        return TextContainerDescription.builder().describeItem(c -> "Dark and a little bit dusty.").build();
    }

    @Bean
    @ViewFor(Table.Box.class)
    public ContainerDescription<String> boxDescription() {
       return TextContainerDescription.builder().describeItem(c -> "Wooden box with a little keyhole.").build();
    }

    @Bean
    @ViewFor(Table.SecretBox.class)
    public ContainerDescription<String> secreteBoxDescription() {
        return TextContainerDescription.builder().describeItem(c -> "Wooden box with a little keyhole.").build();
    }

    @Bean
    @ViewFor(Table.TableBack.class)
    public ContainerDescription<String> tableBackDescription() {
        return TextContainerDescription.builder().describeItem(c -> "Dark and a little bit dusty.").build();
    }

    @Bean
    @ViewFor(Table.OnTable.class)
    public ContainerDescription<String> onTableDescription() {
        return TextContainerDescription.builder().describeItem(c -> "Clean, contains only useful stuff.").build();
    }
}
