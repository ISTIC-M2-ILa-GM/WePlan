package fr.istic.gm.weplan.server.log;

/**
 * Logging message.
 */
public interface LogMessage {
    String API_MESSAGE = "CONTROLLER({}): {} {}";

    String GET_CITIES = "Get cities:";
    String CITIES_GOTTEN = "Cities gotten.";

    String GET_REGIONS = "Get regions:";
    String REGIONS_GOTTEN = "Regions gotten.";
}
