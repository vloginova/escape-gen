package escapegen.basics.lock;

import escapegen.basics.tool.InvisibleInkPaper;
import escapegen.basics.tool.Lamp;
import escapegen.model.Lock;
import escapegen.model.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
public class CodePadlock extends Lock {
    String code;

    public CodePadlock() {
        code = String.format("%03d", (new Random()).nextInt(1000));
        Lamp lamp = new Lamp("Lamp");
        addTool(lamp);
        addTool(new InvisibleInkPaper("Paper", code, lamp));
    }

    @Override
    protected boolean unlock(Tool tool) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Set up correct position of three cylinders on the lock...");

        String inCode;
        try {
            inCode = br.readLine();

            if (!inCode.equals(code)) {
                System.out.println("Nothing happened.");
                return false;
            }

            System.out.println("*Clack*");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
