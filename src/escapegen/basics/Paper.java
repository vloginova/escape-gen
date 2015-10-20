package escapegen.basics;

import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Paper extends Tool {

    private String code;

    public Paper(String id, String code) {
        super(id, Size.Small);
        this.code = code;
    }

    public void examine() {
        System.out.println(code);
    }
}
