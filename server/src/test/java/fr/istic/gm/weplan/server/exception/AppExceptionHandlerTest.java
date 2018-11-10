package fr.istic.gm.weplan.server.exception;

import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.server.config.consts.AppError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AppExceptionHandlerTest {
    @Mock
    private HttpServletResponse mockHttpServletResponse;

    private AppExceptionHandler appExceptionHandler;

    @Before
    public void setUp() {
        this.appExceptionHandler = new AppExceptionHandler();
    }

    @Test
    public void shouldHandleNotFoundException() {
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(AppError.DOMAIN_ENTITY_NOT_FOUND);
        expectedResponse.setObject("City");

        DomainException domainException =
                new DomainException("City not found.", "City", DomainException.ExceptionType.NOT_FOUND);

        ErrorResponse errorResponse =
                this.appExceptionHandler.domainExceptionHandler(this.mockHttpServletResponse, domainException);

        assertThat(errorResponse, equalTo(expectedResponse));
    }

    @Test
    public void shouldHandleForbidden() {
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(AppError.DOMAIN_FORBIDDEN);
        expectedResponse.setObject("City");

        DomainException domainException = new DomainException("City not found.", "City", DomainException.ExceptionType.FORBIDDEN);

        ErrorResponse errorResponse =
                this.appExceptionHandler.domainExceptionHandler(this.mockHttpServletResponse, domainException);

        assertThat(errorResponse, equalTo(expectedResponse));
    }
}