package escapegen.basics.lock;

import escapegen.basics.tool.InvisibleInkPaper;
import escapegen.basics.tool.Lamp;
import escapegen.context.UserIO;
import escapegen.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
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
    protected boolean unlock(Tool tool) {
        String inCode = UserIO.getInstance().readLine();
        return inCode.equals(code);
    }
}
