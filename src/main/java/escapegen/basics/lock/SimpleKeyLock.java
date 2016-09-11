package escapegen.basics.lock;

import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author - Vita Loginova
 */
@Reusable
@ItemProperty(size = ItemProperties.Size.Small)
public class SimpleKeyLock extends Lock {
    @Autowired
    Key key;

    @PostConstruct
    public void configure() {
        tools.add(key);
    }

    @ViewFor(SimpleKeyLock.class)
    @Override
    public void setDescription(LockDescription<?> description) {
        super.setDescription(description);
    }

    @Override
    protected UnlockingResult unlock(Tool tool) {
        if (tools.contains(tool)) {
            tool.setUsed(true);
            return UnlockingResult.SUCCESS;
        }

        return UnlockingResult.FAIL;
    }

    @Reusable
    @ItemProperty(size = ItemProperties.Size.Small)
    public static class Key extends Tool {
        @ViewFor(SimpleKeyLock.Key.class)
        @Override
        public void setDescription(ToolDescription<?> description) {
            super.setDescription(description);
        }
    }
}
