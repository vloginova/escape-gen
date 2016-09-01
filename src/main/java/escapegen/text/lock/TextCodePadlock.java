package escapegen.text.lock;

import escapegen.basics.lock.CodePadlock;
import escapegen.model.LockDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextLockDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextCodePadlock {
    @Bean
    @ViewFor(CodePadlock.class)
    public LockDescription<String> codePadLockDescription() {
        return TextLockDescription.builder()
                .describeItem(l -> "")
                .describeBeforeUnlocking(l -> "Set up correct position of three cylinders on the lock...")
                .describeUnlockingSucceed(l -> "*Clack*").build();
    }
}
