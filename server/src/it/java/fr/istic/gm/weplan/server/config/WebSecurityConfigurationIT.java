package fr.istic.gm.weplan.server.config;

import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.infra.repository.UserRepository;
import fr.istic.gm.weplan.server.App;
import fr.istic.gm.weplan.server.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.someUserDao;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.EMAIL;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.PASWORD;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGIN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class WebSecurityConfigurationIT {

    private static final String FAKE_PASSWORD = "a-fake-password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User entity1;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        User user = someUserDao();
        user.setPassword(passwordEncoder.encode(FAKE_PASSWORD));
        entity1 = userRepository.save(user);
    }

    @Test
    public void shouldLogin() throws Exception {

        MvcResult mvcResult = mockMvc.perform(formLogin(LOGIN).user(EMAIL, entity1.getEmail()).password(PASWORD, FAKE_PASSWORD))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse(), notNullValue());
        assertThat(mvcResult.getResponse().getContentAsString(), notNullValue());

        Map<String, String> map = (Map<String, String>) JsonUtils.fromJson(mvcResult, HashMap.class);

        assertThat(map, notNullValue());
        assertThat(map.get("email"), equalTo(entity1.getEmail()));
        assertThat(map.get("firstName"), equalTo(entity1.getFirstName()));
        assertThat(map.get("lastName"), equalTo(entity1.getLastName()));
        assertThat(map.get("role"), equalTo(entity1.getRole().name()));
    }

    @Test
    public void shouldNotLoginWithWrongPassword() throws Exception {

        mockMvc.perform(formLogin(LOGIN).user(EMAIL, entity1.getEmail()).password(PASWORD, "wrong-password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldNotLoginWithWrongUser() throws Exception {

        mockMvc.perform(formLogin(LOGIN).user(EMAIL, "wrong-email").password(PASWORD, FAKE_PASSWORD))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldLogout() throws Exception {

        MvcResult mvcResult = mockMvc.perform(logout())
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo(LOGIN + "?logout"));

    }

    @Test
    public void shouldNotLogout() throws Exception {

        MvcResult mvcResult = mockMvc.perform(logout())
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo(LOGIN + "?logout"));
    }
}
