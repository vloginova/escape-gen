package escapegen.text.tool;

import escapegen.basics.tool.InvisibleInkPaper;
import escapegen.model.ToolDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextToolDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextInvisibleInkPaper {
    @Bean
    @ViewFor(InvisibleInkPaper.class)
    public ToolDescription<String> inkPaperDescription() {
        return TextToolDescription.builder()
                .describeItem(c -> ((InvisibleInkPaper)c).getText())
                .describeApplySucceed((c, applied) -> "The text changed!").build();
    }
}
