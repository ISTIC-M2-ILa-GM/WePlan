package fr.istic.gm.weplan.infra.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static fr.istic.gm.weplan.infra.TestData.SOME_STRING;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordEncoderWrapperTest {

    private PasswordEncoderWrapper passwordEncoderWrapper;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Before
    public void setUp() {
        passwordEncoderWrapper = new PasswordEncoderWrapper(mockPasswordEncoder);
    }

    @Test
    public void shouldEncodeAPassword() {

        when(mockPasswordEncoder.encode(any())).thenReturn(SOME_STRING);

        String encode = passwordEncoderWrapper.encode(SOME_STRING + 1);

        verify(mockPasswordEncoder).encode(SOME_STRING + 1);

        assertThat(encode, notNullValue());
        assertThat(encode, equalTo(SOME_STRING));
    }
}
