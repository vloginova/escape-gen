package escapegen.text.container;

import escapegen.basics.container.Trunk;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextTrunk {
    @Bean
    @ViewFor(Trunk.class)
    public ContainerDescription<String> trunkDescription() {
        return TextContainerDescription.builder().describeItem(c -> "Very old trunk.").build();
    }
}
