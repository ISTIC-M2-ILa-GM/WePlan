package fr.istic.gm.weplan.infra.client.weather.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "weplan.weather", ignoreUnknownFields = false)
public class WeatherProperties {
    private String apiKey;
}
