package escapegen.context;

import org.springframework.stereotype.Component;

/**
 * @author - Vita Loginova
 */
@Component
public class UserIOConsole implements UserPrinter<String> {
    @Override
    public void print(String string) {
        System.out.println(string);
    }

    @Override
    public void println(String data) {
        System.out.print(data);
    }
}
