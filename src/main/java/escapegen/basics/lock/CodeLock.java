package escapegen.basics.lock;

import escapegen.basics.tool.Paper;
import escapegen.model.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
@Reusable
@ItemProperty
public class CodeLock extends Lock {

    private int code;
    @Resource(name = "paper")
    Paper leftPart;
    @Resource(name = "paper")
    Paper rightPart;

    @ViewFor(CodeLock.class)
    @Override
    public void setDescription(LockDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    private void configure() {
        code = (new Random()).nextInt(10000);
        leftPart.setText(String.format("%02d...", code / 100));
        rightPart.setText(String.format("...%02d", code % 100));
        addTool(leftPart);
        addTool(rightPart);
    }

    @Override
    protected UnlockingResult unlock(Tool tool) {
        return new UnlockingResult(this::inputCode);
    }

    private UnlockingResult inputCode(String inCode) {
        isUnlocked = inCode.equals(String.format("%04d", code));
        return isUnlocked ? UnlockingResult.SUCCESS: UnlockingResult.FAIL;
    }
}
