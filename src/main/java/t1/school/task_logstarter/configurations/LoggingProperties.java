package t1.school.task_logstarter.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom-logging")
public class LoggingProperties {
    private boolean enable = true;
    private String level = "INFO";

    public boolean isEnable() { return enable; }
    public void setEnable(boolean enable) { this.enable = enable; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
