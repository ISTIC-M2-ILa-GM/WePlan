package fr.istic.gm.weplan.server.log;

/**
 * Logging message.
 */
public interface LogMessage {
    String CREATE_CITY = "Create city:";
    String CITY_CREATED = "City created:";
    String GET_CITY = "Get city...";
    String DELETE_CITY = "Delete city...";
    String CITY_DELETED = "City deleted.";
    String PATCH_CITY = "Patch city:";
    String CITY_PATCHED = "City patched:";
    String CITY_GOTTEN = "City gotten:";
    String API_MESSAGE = "CONTROLLER({}): {} {}";

    String GET_CITIES = "Get cities:";
    String CITIES_GOTTEN = "Cities gotten.";

    String GET_REGIONS = "Get regions:";
    String REGIONS_GOTTEN = "Regions gotten.";
}
