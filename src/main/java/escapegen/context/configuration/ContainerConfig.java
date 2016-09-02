package escapegen.context.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vita on 9/2/16.
 */
@Getter
@Setter
public class ContainerConfig {
    List<String> content;
    List<String> tools;
}
