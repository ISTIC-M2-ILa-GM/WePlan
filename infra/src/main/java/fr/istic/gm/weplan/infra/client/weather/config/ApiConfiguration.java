package fr.istic.gm.weplan.infra.client.weather.config;

import fr.istic.gm.weplan.infra.client.weather.ApiClient;
import fr.istic.gm.weplan.infra.client.weather.config.properties.WeatherProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(WeatherProperties.class)
public class ApiConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }
}
