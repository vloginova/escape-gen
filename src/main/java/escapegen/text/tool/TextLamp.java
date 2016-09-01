package escapegen.text.tool;

import escapegen.basics.tool.Lamp;
import escapegen.model.ToolDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextToolDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextLamp {
    @Bean
    @ViewFor(Lamp.class)
    public ToolDescription<String> lampDescription() {
        return TextToolDescription.builder()
                .describeItem(t -> "Ordinary table lamp.")
                .describeApplySucceed((t, applied) -> "Something happened to paper, look at it.").build();
    }
}
