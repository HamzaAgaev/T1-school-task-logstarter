package t1.school.task_logstarter.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import t1.school.task_logstarter.aspects.LoggingAspect;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "custom-logging.enable", havingValue = "true", matchIfMissing = true)
    public LoggingAspect loggingAspect(LoggingProperties loggingProperties) {
        String level = loggingProperties.getLevel();
        if (level == null) {
            level = "INFO";
        }
        return new LoggingAspect(level);
    }
}
