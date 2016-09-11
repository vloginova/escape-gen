package escapegen.text.container;

import escapegen.basics.container.Bed;
import escapegen.model.ContainerDescription;
import escapegen.model.ViewFor;
import escapegen.text.TextContainerDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - Vita Loginova
 */
@Configuration
public class TextBed {
    @Bean
    @ViewFor(Bed.class)
    public ContainerDescription<String> bedDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "A quite small made up twin-size bed.").build();
    }

    @Bean
    @ViewFor(Bed.Pillow.class)
    public ContainerDescription<String> pillowDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "Just a soft pillow. *Yawn*")
                .describeContent(c -> "Something fell out of the pillow!").build();
    }

    @Bean
    @ViewFor(Bed.OnBed.class)
    public ContainerDescription<String> onBedDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "Simple blue and white bed linen.").build();
    }

    @Bean
    @ViewFor(Bed.UnderBed.class)
    public ContainerDescription<String> underBedDescription() {
        return TextContainerDescription.builder()
                .describeItem(c -> "Dark but clean.").build();
    }
}
