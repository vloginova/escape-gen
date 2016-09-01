package escapegen;

import escapegen.basics.container.Bed;
import escapegen.context.Game;
import escapegen.context.Generator;
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


        Generator generator = context.getBean(Generator.class);
        Game game = generator.generate(10);
        new CommandLine(game).start();
        context.close();
    }
}
