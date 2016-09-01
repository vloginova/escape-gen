package escapegen.context;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author - Vita Loginova
 */
@Component
public class UserIOConsole implements UserIO<String> {
    @Override
    public void write(String string) {
        System.out.println(string);
    }

    @SneakyThrows
    @Override
    public String read() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
