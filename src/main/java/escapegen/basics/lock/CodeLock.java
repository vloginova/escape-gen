package escapegen.basics.lock;

import escapegen.basics.tool.Paper;
import escapegen.context.UserIO;
import escapegen.model.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
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
    protected boolean unlock(Tool tool) {
        String inCode = UserIO.getInstance().readLine();
        return inCode.equals(String.format("%04d", code));
    }
}
