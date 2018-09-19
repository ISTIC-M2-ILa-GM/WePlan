package fr.istic.gm.weplan.domain.log;

/**
 * Logging message.
 */
public interface LogMessage {
    String PATCH_CITY = "Patch city:";
    String CITY_PATCHED = "City patched:";
    String CREATE_CITY = "Create city:";
    String CITY_CREATED = "City created:";
    String GET_CITY = "Get city...";
    String CITY_GOTTEN = "City gotten:";
    String DELETE_CITY = "Delete city...";
    String CITY_DELETED = "City deleted.";
    String SERVICE_MESSAGE = "SERVICE({}): {} {}";
    String GET_CITIES = "Get cities:";
    String CITIES_GOTTEN = "Cities gotten.";

    String POST_REGION = "Post region: ";
    String REGION_POSTED = "Region posted.";
}
