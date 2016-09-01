package escapegen.text;

import escapegen.model.Item;
import escapegen.model.Lock;
import escapegen.model.LockDescription;
import lombok.Builder;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author - Vita Loginova
 */
public class TextLockDescription extends TextItemDescription implements LockDescription<String> {
    private Function<Lock, String> describeBeforeUnlocking;
    private Function<Lock, String> describeUnlockingFailed;
    private Function<Lock, String> describeUnlockingSucceed;

    @Builder
    private TextLockDescription(Function<Item, String> describeItem,
                        BiFunction<Item, Item, String> describeApplyFailed,
                        BiFunction<Item, Item, String> describeApplySucceed,
                        Function<Lock, String> describeBeforeUnlocking,
                        Function<Lock, String> describeUnlockingFailed,
                        Function<Lock, String> describeUnlockingSucceed) {
        super(describeItem, describeApplyFailed, describeApplySucceed);
        this.describeBeforeUnlocking = describeBeforeUnlocking;
        this.describeUnlockingFailed = describeUnlockingFailed;
        this.describeUnlockingSucceed = describeUnlockingSucceed;
    }

    @Override
    public String describeBeforeUnlocking(Lock l) {
        return describeBeforeUnlocking.apply(l);
    }

    @Override
    public String describeUnlockingFailed(Lock l) {
        return describeUnlockingFailed.apply(l);
    }

    @Override
    public String describeUnlockingSucceed(Lock l) {
        return describeUnlockingSucceed.apply(l);
    }
}
