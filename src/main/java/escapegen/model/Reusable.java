package escapegen.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by vita on 9/1/16.
 */
@Component
@Lazy
@Scope("prototype")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Reusable {
}
