package escapegen.text.tool;

import escapegen.basics.tool.Paper;
import escapegen.model.ToolDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextToolDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextPaper {
    @Bean
    @ViewFor(Paper.class)
    public ToolDescription<String> paperDescription() {
        return TextToolDescription.builder().describeItem(c -> ((Paper) c).getText()).build();
    }
}
