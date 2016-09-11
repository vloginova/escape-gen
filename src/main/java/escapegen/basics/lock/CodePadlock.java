package escapegen.basics.lock;

import escapegen.basics.tool.InvisibleInkPaper;
import escapegen.basics.tool.Lamp;
import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
@Reusable
@ItemProperty
public class CodePadlock extends Lock {
    String code;
    @Autowired
    Lamp lamp;
    @Autowired
    InvisibleInkPaper invisibleInkPaper;

    @ViewFor(CodePadlock.class)
    @Override
    public void setDescription(LockDescription<?> description) {
        super.setDescription(description);
    }

    @PostConstruct
    public void configure() {
        code = String.format("%03d", (new Random()).nextInt(1000));
        invisibleInkPaper.setInvisibleText(code);
        invisibleInkPaper.setLamp(lamp);
        addTool(lamp);
        addTool(invisibleInkPaper);
    }

    @Override
    protected UnlockingResult unlock(Tool tool) {
        return new UnlockingResult(this::inputCode);
    }

    private UnlockingResult inputCode(String inCode) {
        isUnlocked = inCode.equals(code);
        return isUnlocked ? UnlockingResult.SUCCESS : UnlockingResult.FAIL;
    }
}
