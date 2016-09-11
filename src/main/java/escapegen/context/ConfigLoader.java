package escapegen.context;

import escapegen.context.configuration.GameConfig;
import escapegen.model.AbstractContainer;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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

    private List<String> goals;
    private List<String> rootContainers;
    private List<String> rooms;

    private final Random random = new Random();

    public void configure(GameConfig config) {
        if (config == null)
            throw new NullPointerException("Config cannot be null");
        goals = new LinkedList<>();
        goals.add(config.getGoalBeanClassName());
        rooms = new LinkedList<>();
        rooms.add(config.getRoomBeanClassName());
        rootContainers = new LinkedList<>(config.getBasicsBeanClassNames());
    }

    @SneakyThrows
    private AbstractContainer getByName(String className) {
        Class<?> type = Class.forName(className);
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
