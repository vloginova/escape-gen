package escapegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import escapegen.context.Game;
import escapegen.context.configuration.ConfigRepository;
import escapegen.context.configuration.GameConfig;
import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author - Vita Loginova
 */
public class Launcher {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        System.out.println("Where am I?");
//        context.getBean(CommandLine.class).start();
        ConfigRepository repository = context.getBean(ConfigRepository.class);
        GameConfig config = context.getBean(Game.class).getConfiguration();
        String name = "TestConfig";
        config.setName(name);
        System.out.println(objectToJson(config));
        repository.save(config);
        System.out.println("------------");
        GameConfig loaded = repository.findByName(name).get(0);
        System.out.println(objectToJson(loaded));
        context.close();
    }

    @SneakyThrows
    private static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
