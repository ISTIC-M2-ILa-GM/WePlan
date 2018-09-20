package fr.istic.gm.weplan.server.exception;

public interface AppError {
    // Domain related errors
    String DOMAIN_ENTITY_NOT_FOUND = "weplan.error.domain.not_found";
    String DOMAIN_BAD_REQUEST = "weplan.error.domain.bad_request";
    String DOMAIN_INTERNAL_ERROR = "weplan.error.domain.internal_error";
}
