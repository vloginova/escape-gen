package escapegen.text.container;

import escapegen.basics.container.Room;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextRoom {
    @Bean
    @ViewFor(Room.class)
    public ContainerDescription<String> roomDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "A large light room. It's pretty, but I need to get out of here.")
                .build();
    }
}
