package te.simpleApps.contentSearcher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "contentSearcher")
@Configuration
public class Config {
    private Boolean showColors;

    public Boolean getShowColors() {
        return showColors;
    }

    public void setShowColors(Boolean showColors) {
        this.showColors = showColors;
    }
}