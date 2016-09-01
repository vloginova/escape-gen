package escapegen.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vita on 9/1/16.
 */
@Component
public class ItemPropertyBeanPostProcessor implements BeanPostProcessor {
    private Set<String> itemIds = new HashSet<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> type = bean.getClass();
        ItemProperty annotation = type.getAnnotation(ItemProperty.class);
        boolean isItem = bean instanceof Item;

        if (annotation == null && isItem) {
            throw new IllegalStateException("Item must have ItemProperty");
        } else if (annotation != null && !isItem) {
            throw new IllegalStateException("Only Item instances can have ItemProperty");
        }

        if (annotation != null) {
            ItemProperties properties = ItemProperties.builder()
                    .matter(annotation.matter())
                    .shape(annotation.shape())
                    .size(annotation.size())
                    .id(generateId(type)).build();
            ((Item) bean).setItemProperties(properties);
        }
        return bean;
    }

    private String generateId(Class<?> type) {
        String id = type.getSimpleName();
        if (itemIds.contains(id)) {
            int i = 1;
            while (itemIds.contains(id + ++i)) {
                /* skip */
            }
            id += i;
        }
        itemIds.add(id);
        return id;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
