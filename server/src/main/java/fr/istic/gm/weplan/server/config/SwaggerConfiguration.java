package fr.istic.gm.weplan.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${version}")
    private String version;

    @Bean
    public Docket api(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.istic.gm.weplan.server.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("WePlan")
                .description("Weekend Planning Project.")
                .license("GPL v3")
                .licenseUrl("https://github.com/ISTIC-M2-ILa-GM/WePlan/blob/master/LICENSE")
                .version(version)
                .contact(new Contact("WePlan", "https://github.com/ISTIC-M2-ILa-GM/WePlan", null))
                .build();
    }
}