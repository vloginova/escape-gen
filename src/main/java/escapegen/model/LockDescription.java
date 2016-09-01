package escapegen.model;

/**
 * @author - Vita Loginova
 */
public interface LockDescription<T> extends ItemDescription<T> {
    T describeBeforeUnlocking(Lock l);
    T describeUnlockingFailed(Lock l);
    T describeUnlockingSucceed(Lock l);
}
