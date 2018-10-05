package fr.istic.gm.weplan.server.config.consts;

/**
 * List all routes of the API
 */
public final class ApiRoutes {

    public static final String ID = "/{id}";
    public static final String API = "/api";
    public static final String CITY = API + "/city";
    public static final String DEPARTMENT = API + "/department";
    public static final String REGION = API + "/region";
    public static final String ACTIVITY = API + "/activity";
    public static final String USER = API + "/user";

    private ApiRoutes() {
    }
}
