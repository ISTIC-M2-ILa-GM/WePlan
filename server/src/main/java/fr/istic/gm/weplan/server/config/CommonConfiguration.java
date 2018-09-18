package fr.istic.gm.weplan.server.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan("fr.istic.gm.weplan.domain.model.entities")
@EnableJpaRepositories("fr.istic.gm.weplan.infra.repository")
public class CommonConfiguration {
}
