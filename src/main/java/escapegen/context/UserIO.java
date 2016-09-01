package escapegen.context;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author - Vita Loginova
 */
public class UserIO {
    private static UserIO ourInstance = new UserIO();

    public static UserIO getInstance() {
        return ourInstance;
    }

    private UserIO() {
    }

    public void writeString(String string) {
        System.out.println(string);
    }

    @SneakyThrows
    public String readLine() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
