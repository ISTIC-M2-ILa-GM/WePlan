package fr.istic.gm.weplan.server.log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LoggingHandlerTest {

    private LoggingHandler loggingHandler;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Before
    public void setUp() {
        loggingHandler = new LoggingHandler(mockHttpServletRequest);
    }

    @Test
    public void shouldReturnEmptyWhenRequestIsNotNativeWebRequest() {
        assertThat(loggingHandler.findUserId(mock(RequestAttributes.class)), equalTo(""));
    }
}
