package escapegen.context.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vita on 9/2/16.
 */
@Getter
@Setter
@Entity
public class GameConfig {
    @Id
    @GeneratedValue
    private String configId;
    private String name;
    private String goalBeanClassName;
    private String roomBeanClassName;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<String> basicsBeanClassNames;
    //private Map<String, ContainerConfig> containerConfigs;
    //private Map<String, Class<?>> items;
    //private Map<String, FurnitureConfig> furnitureConfigs;
    //private List<String> inventory;
}
