package escapegen.context;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Created by vita on 9/1/16.
 */
@Component
public class UserIOWeb implements UserIO<String> {
    private final StringBuilder stringBuilder = new StringBuilder();
    @Getter
    private boolean inputNeeded;

    @Override
    public void write(String data) {
        String fixed = data.replace("\n", "<br>");
        stringBuilder.append(fixed).append("<br>");
    }

    @Override
    public String read() {
        inputNeeded = true;
        return null;
    }

    public String retrieveBufferedData() {
        String result = stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }
}
