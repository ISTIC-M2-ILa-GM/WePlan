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
    public static final String NOTHING_TO_PATCH = "There's nothing to patch.";
    public static final String WRONG_DATA_TO_PATCH = "The data to patch is wrong.";

    private final String object;
    private final ExceptionType type;

    public DomainException(String message, String object, ExceptionType type) {
        super(String.format(message, object));
        this.type = type;
        this.object = object;
    }

    public enum ExceptionType {
        NOT_FOUND,
        BAD_REQUEST
    }
}
