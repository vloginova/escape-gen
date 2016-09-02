package escapegen.context.configuration;

import escapegen.model.Furniture;

import java.util.Map;

/**
 * Created by vita on 9/2/16.
 */
public class FurnitureConfig extends ContainerConfig {
    Map<Furniture.Space, String> spaceContent;
}
