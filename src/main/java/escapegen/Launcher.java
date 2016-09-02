package escapegen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author - Vita Loginova
 */
public class Launcher {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        System.out.println("Where am I?");
        context.getBean(CommandLine.class).start();
        context.close();
    }
}
