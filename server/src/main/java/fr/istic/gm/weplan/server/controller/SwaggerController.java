package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.server.App;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.InputStream;

@Path("/")
public class SwaggerController {
    @GET
    @Path("{path:.*}")
    public byte[] Get(@PathParam("path") String path) {
        try {
            if ("".equals(path)) {
                path = "index.html";
            }
            InputStream resource = App.class.getClassLoader().getResourceAsStream("META-INF/resources/webjars/swagger-ui/3.18.2/" + path);
            return IOUtils.toByteArray(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
