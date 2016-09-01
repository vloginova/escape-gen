package escapegen;

import escapegen.text.TextDescriptionConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author - Vita Loginova
 */
@Configuration
@ComponentScan(basePackages = "escapegen")
@Import(TextDescriptionConfig.class)
public class JavaConfig {

}
