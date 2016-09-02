package escapegen;

import com.mongodb.MongoClient;
import escapegen.text.TextDescriptionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * @author - Vita Loginova
 */
@Configuration
@ComponentScan(basePackages = "escapegen")
@Import(TextDescriptionConfig.class)
@EnableMongoRepositories
public class JavaConfig {
    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoTemplate gameConfigTemplate = new MongoTemplate(mongoClient(), "gameConfigTemplate");
        return gameConfigTemplate;
    }

    @Bean
    public String getStr() {
        return "xx";
    }
}
