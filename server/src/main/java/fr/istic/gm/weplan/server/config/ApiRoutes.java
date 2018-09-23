package fr.istic.gm.weplan.server.config;

/**
 * List all routes of the API
 */
public interface ApiRoutes {

    String ID = "/{id}";
    String API = "/api";
    String CITY = API + "/city";
    String DEPARTMENT = API + "/department";
    String REGION = API +"/region";
}
