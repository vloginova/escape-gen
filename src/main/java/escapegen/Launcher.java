package escapegen;

import escapegen.basics.container.Bed;
import escapegen.context.Game;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author - Vita Loginova
 */
public class Launcher {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        System.out.println("Where am I?");

        Bed bed = context.getBean(Bed.class);
        System.out.println(bed.getDescription().describeContent(bed));


        Game game = context.getBean(Game.class);
        new CommandLine(game).start();
        context.close();
    }
}
