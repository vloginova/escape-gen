package escapegen.context;

import escapegen.context.configuration.GameConfig;
import escapegen.model.AbstractContainer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

/**
 * Created by vita on 9/2/16.
 */
@Component
@Getter
public class ConfigLoader extends GameLoaderImpl {
    @Autowired
    private ApplicationContext context;
    @Setter
    private GameConfig config;

    private List<String> goals;
    private List<String> rootContainers;
    private List<String> rooms;

    private final Random random = new Random();

    @PostConstruct
    public void configure() {
//        goals = Collections.singletonList(config.getGoalBeanClassName());
//        rooms = Collections.singletonList(config.getRoomBeanClassName());
//        rootContainers = new LinkedList<>(config.getBasicsBeanClassNames());
    }

    private AbstractContainer getByName(String className) {
        Class<?> type = ClassUtils.resolveClassName(className, ClassLoader.getSystemClassLoader());
        return (AbstractContainer) context.getBean(type);
    }

    @Override
    protected AbstractContainer genContainer(Random random) {
        return getByName(rootContainers.remove(0));
    }

    @Override
    protected AbstractContainer genGoal(Random random) {
        return getByName(goals.remove(0));
    }

    @Override
    protected AbstractContainer genRoom(Random random) {
        return getByName(getRooms().remove(0));
    }

    @Override
    protected boolean isContainersEmpty() {
        return rootContainers.isEmpty();
    }
}
