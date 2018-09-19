package fr.istic.gm.weplan.server.exception;

import fr.istic.gm.weplan.domain.exception.DomainException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AppExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = DomainException.class)
    public ErrorResponse domainExceptionHandler(HttpServletResponse response, DomainException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getType()) {
            case NOT_FOUND:
                errorResponse.setCode(AppError.DOMAIN_ENTITY_NOT_FOUND);
                break;
            case BAD_REQUEST:
                errorResponse.setCode(AppError.DOMAIN_BAD_REQUEST);
                break;
            default:
                errorResponse.setCode(AppError.DOMAIN_INTERNAL_ERROR);
                break;
        }
        errorResponse.setObject(e.getObject());

        return errorResponse;
    }
}
