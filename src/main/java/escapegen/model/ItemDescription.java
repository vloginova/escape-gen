package escapegen.model;

/**
 * @author - Vita Loginova
 */
public interface ItemDescription<R> {
    R describeItem(Item i);
    R describeApplyFailed(Item i, Item applied);
    R describeApplySucceed(Item i, Item applied);
}
