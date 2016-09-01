package escapegen.text.lock;

import escapegen.basics.lock.CodeLock;
import escapegen.model.LockDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextLockDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextCodeLock {
    @Bean
    @ViewFor(CodeLock.class)
    public LockDescription<String> codeLockDescription() {
        return TextLockDescription.builder()
                .describeItem(l -> "A code lock. It seems like a code has four numbers in it.")
                .describeBeforeUnlocking(l -> "Please, enter the code...")
                .describeUnlockingFailed(l -> "Something went wrong.")
                .describeUnlockingSucceed(l -> "Hurray, it's unlocked!").build();
    }
}
