package escapegen.basics.lock;

import escapegen.basics.tool.Paper;
import escapegen.model.Lock;
import escapegen.model.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @author - Vita Loginova
 */
public class CodeLock extends Lock {

    private int code;

    public CodeLock() {
        code = (new Random()).nextInt(10000);
        addTool(new Paper("PaperL", String.format("%02d...", code / 100)));
        addTool(new Paper("PaperR", String.format("...%02d", code % 100)));
    }

    @Override
    protected boolean unlock(Tool tool) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, enter the code...");

        String inCode;
        try {
            inCode = br.readLine();

            if (!inCode.equals(String.format("%04d", code))) {
                System.out.println("Something went wrong.");
                return false;
            }

            System.out.println("Hurray, it's unlocked!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String toString() {
        return "A code lock. It seems like a code has four numbers in it.";
    }
}
