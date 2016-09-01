package escapegen.model;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author - Vita Loginova
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ItemProperty {
    ItemProperties.Size size() default ItemProperties.Size.Medium;
    ItemProperties.Matter matter() default ItemProperties.Matter.General;
    ItemProperties.Shape shape() default ItemProperties.Shape.General;
}