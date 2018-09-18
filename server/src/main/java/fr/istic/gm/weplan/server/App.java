package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.server.controller.RegionController;
import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    public static void main(String[] args) {
        UndertowJaxrsServer ut = new UndertowJaxrsServer();

        ut.deploy(new App());

        ut.start(Undertow.builder().addHttpListener(8080, "localhost"));
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(RegionController.class);
        return classes;
    }
}
