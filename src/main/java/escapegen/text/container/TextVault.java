package escapegen.text.container;

import escapegen.basics.container.Vault;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextVault {
    @Bean
    @ViewFor(Vault.class)
    public ContainerDescription<String> vaultDescription() {
        return TextContainerDescription.builder().describeItem(c -> "A solid safe with a number lock.").build();
    }
}
