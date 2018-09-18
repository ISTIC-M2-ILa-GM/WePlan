package fr.istic.gm.weplan.domain.log;

/**
 * Logging message.
 */
public interface LogMessage {
    String CREATE_CITY = "Create city:";
    String CITY_CREATED = "City created:";
    String GET_CITY = "Get city...";
    String CITY_GOTTEN = "City gotten:";
    String DELETE_CITY = "Delete city...";
    String CITY_DELETED = "City deleted.";
    String SERVICE_MESSAGE = "SERVICE({}): {} {}";
    String GET_CITIES = "Get cities:";
    String CITIES_GOTTEN = "Cities gotten.";
}
