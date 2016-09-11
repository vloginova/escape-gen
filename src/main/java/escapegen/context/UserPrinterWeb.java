package escapegen.context;

import org.springframework.stereotype.Component;

/**
 * Created by vita on 9/1/16.
 */
@Component
public class UserPrinterWeb implements UserPrinter<String> {
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void print(String data) {
        String fixed = data.replace("\n", "<br>");
        stringBuilder.append(fixed);
    }

    @Override
    public void println(String data) {
        print(data);
        stringBuilder.append("<br>");
    }

    public String retrieveBufferedData() {
        String result = stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }
}
