package escapegen.context.configuration;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vita on 9/2/16.
 */
@Repository
public interface ConfigRepository extends MongoRepository<GameConfig, String> {
    List<GameConfig> findByName(String name);
    GameConfig findByConfigId(String id);
}
