package escapegen.model;

/**
 * @author - Vita Loginova
 */
public interface ContainerDescription<R> extends ItemDescription<R> {
    R describeEmptyContent(Container c);
    R describeContent(Container c);
}
