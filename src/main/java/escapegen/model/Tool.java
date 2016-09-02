package escapegen.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Tools are basic object that primarily used for unlocking different containers.
 * For example, this might be a key or a part of some code or a hammer.
 *
 * @author Vita Loginova
 */
@Getter
@Setter
public abstract class Tool extends AbstractItem {
    ToolDescription<?> description;
    private boolean isUsed = false;
}
