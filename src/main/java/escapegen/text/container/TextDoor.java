package escapegen.text.container;

import escapegen.basics.container.Door;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextDoor {
    @Bean
    @ViewFor(Door.class)
    public ContainerDescription<String> doorDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "Looks tough, don't think I can broke it. But there is a keyhole.")
                .describeEmptyContent(c -> "Congrats!")
                .describeContent(c -> "Congrats!").build();
    }
}
