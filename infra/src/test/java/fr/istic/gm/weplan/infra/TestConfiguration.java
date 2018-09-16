package fr.istic.gm.weplan.infra;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"fr.istic.gm.weplan.*"})
@ComponentScan("fr.istic.gm.weplan.*")
@EntityScan("fr.istic.gm.weplan.*")
public class TestConfiguration {
}
