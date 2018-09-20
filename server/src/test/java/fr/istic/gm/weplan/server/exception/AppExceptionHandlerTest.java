package fr.istic.gm.weplan.server.exception;

import fr.istic.gm.weplan.domain.exception.DomainException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

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
    public void shouldDomainExceptionHandler() {
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(AppError.DOMAIN_ENTITY_NOT_FOUND);
        expectedResponse.setObject("City");

        DomainException domainException =
                new DomainException("City not found.", "City", DomainException.ExceptionType.NOT_FOUND);

        ErrorResponse errorResponse =
                this.appExceptionHandler.domainExceptionHandler(this.mockHttpServletResponse, domainException);

        assertThat(errorResponse, equalTo(expectedResponse));
    }
}