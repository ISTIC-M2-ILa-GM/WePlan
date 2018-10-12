package fr.istic.gm.weplan.server.service.impl;

import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.service.UserDaoService;
import fr.istic.gm.weplan.server.model.SecurityUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static fr.istic.gm.weplan.server.TestData.EMAIL;
import static fr.istic.gm.weplan.server.TestData.someUserDao;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    private UserDetailsService userDetailsService;

    @Mock
    private UserDaoService mockUserService;

    @Before
    public void setUp() {
        userDetailsService = new UserDetailsServiceImpl(mockUserService);
    }

    @Test
    public void shouldLoadUserByUserName() {

        User user = someUserDao();

        when(mockUserService.getUserDaoByEmail(anyString())).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(EMAIL);

        verify(mockUserService).getUserDaoByEmail(EMAIL);

        assertThat(userDetails, notNullValue());
        assertThat(userDetails, instanceOf(SecurityUser.class));
        assertThat(((SecurityUser) userDetails).getId(), equalTo(user.getId()));
        assertThat(userDetails.getUsername(), equalTo(user.getEmail()));
        assertThat(userDetails.getAuthorities(), hasSize(1));
        assertThat(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(user.getRole().name())), equalTo(true));
    }
}
