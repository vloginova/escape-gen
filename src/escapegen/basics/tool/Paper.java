package escapegen.basics.tool;

import escapegen.model.Tool;

/**
 * @author - Vita Loginova
 */
public class Paper extends Tool {

    private String text;

    public Paper(String id, String text) {
        super(id);
        setSize(Size.Small);
        this.text = text;
    }

    public void examine() {
        System.out.println(text);
    }
}
