package fr.istic.gm.weplan.server.exception;

import fr.istic.gm.weplan.domain.exception.DomainException;

public class ServerException extends DomainException {
    public ServerException(String message, String object, ExceptionType type) {
        super(message, object, type);
    }
}
