package escapegen.model;

import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
//@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface Goal {
}
