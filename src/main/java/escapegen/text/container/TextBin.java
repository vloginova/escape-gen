package escapegen.text.container;

import escapegen.basics.container.Bin;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextBin {
    @Bean
    @ViewFor(Bin.class)
    public ContainerDescription<String> binDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "A black plastic recycle bin.")
                .describeEmptyContent(c -> "A couple of useless creased pieces of paper.")
                .build();
    }
}
