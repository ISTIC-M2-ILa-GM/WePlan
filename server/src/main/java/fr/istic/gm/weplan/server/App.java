package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.server.controller.CityController;
import fr.istic.gm.weplan.server.controller.SwaggerController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {

    public static void main(String[] args) {

        UndertowJaxrsServer ut = new UndertowJaxrsServer();
        ut.deploy(new App());

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.0-alpha");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("fr.istic.gm.weplan.server.controller");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);

        ut.start(Undertow.builder().addHttpListener(8080, "localhost"));
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazzes = new HashSet<>();
        clazzes.add(CityController.class);
        clazzes.add(ApiListingResource.class);
        clazzes.add(SwaggerSerializers.class);
        clazzes.add(SwaggerController.class);
        return clazzes;
    }
}
