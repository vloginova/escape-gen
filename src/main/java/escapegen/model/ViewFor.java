package escapegen.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author - Vita Loginova
 */
@Qualifier
@Autowired
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewFor {
    Class<?> value();
}
