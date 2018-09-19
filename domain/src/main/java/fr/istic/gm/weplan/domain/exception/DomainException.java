package fr.istic.gm.weplan.domain.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The Domain exception
 */
@Getter
@Slf4j
public class DomainException extends RuntimeException {

    public static final String NOT_FOUND_MSG = "The %s is not found.";

    private String object;
    private ExceptionType type;

    public DomainException(String message, String object, ExceptionType type) {
        super(String.format(message, object));
        this.type = type;
        this.object = object;
    }

    public enum ExceptionType {
        NOT_FOUND
    }
}
