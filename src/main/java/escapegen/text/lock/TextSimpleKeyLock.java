package escapegen.text.lock;

import escapegen.basics.lock.SimpleKeyLock;
import escapegen.model.LockDescription;
import escapegen.model.ToolDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextLockDescription;
import escapegen.text.TextToolDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextSimpleKeyLock {
    @Bean
    @ViewFor(SimpleKeyLock.class)
    public LockDescription<String> simpleKeyLockDescription() {
        return TextLockDescription.builder()
                .describeItem(l -> "Small golden keyhole.")
                .describeUnlockingSucceed(l -> "*Clank!*").build();
    }

    @Bean
    @ViewFor(SimpleKeyLock.Key.class)
    public ToolDescription<String> keyDescription() {
        return TextToolDescription.builder().describeItem(t -> "A little key.").build();
    }
}
