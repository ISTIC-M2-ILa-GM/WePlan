package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RoleDto;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.infra.repository.UserRepository;
import fr.istic.gm.weplan.server.App;
import fr.istic.gm.weplan.server.config.CommonConfiguration;
import fr.istic.gm.weplan.server.service.impl.AuthService;
import fr.istic.gm.weplan.server.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.firstPageOptions;
import static fr.istic.gm.weplan.server.TestData.someUserDao;
import static fr.istic.gm.weplan.server.TestData.someUserRequest;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGIN;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER;
import static fr.istic.gm.weplan.server.utils.JsonUtils.parseToJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AuthService mockAuthService;

    private User entity1;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        entity1 = userRepository.save(someUserDao());
        userRepository.save(someUserDao());

        when(mockAuthService.loggedUserId()).thenReturn(entity1.getId());
    }

    @Test
    @WithMockUser
    public void shouldGetUsers() throws Exception {

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(0);
        pageRequest.setSize(10);

        MvcResult mvcResult = mockMvc.perform(get(USER)
                .content(parseToJson(pageRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PageDto<UserDto> response = (PageDto<UserDto>) JsonUtils.fromJson(mvcResult, PageDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getTotalPages(), equalTo(1));
        assertThat(response.getSize(), equalTo(10));
        assertThat(response.getResults(), hasSize(2));
    }

    @Test
    public void shouldNotGetUsersWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(USER)
                .content(parseToJson(firstPageOptions()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldGetAUser() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(USER + ID, entity1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto response = (UserDto) JsonUtils.fromJson(mvcResult, UserDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(entity1.getId()));
        assertThat(response.getRole().name(), equalTo(entity1.getRole().name()));
        assertThat(response.getLastName(), equalTo(entity1.getLastName()));
        assertThat(response.getFirstName(), equalTo(entity1.getFirstName()));
        assertThat(response.getEmail(), equalTo(entity1.getEmail()));
    }

    @Test
    public void shouldNotGetAnUserWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(USER + ID, entity1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    public void shouldCreateAUser() throws Exception {

        UserRequest userRequest = someUserRequest();

        MvcResult mvcResult = mockMvc.perform(post(USER)
                .content(parseToJson(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto response = (UserDto) JsonUtils.fromJson(mvcResult, UserDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), notNullValue());
        assertThat(response.getFirstName(), equalTo(userRequest.getFirstName()));
        assertThat(response.getEmail(), equalTo(userRequest.getEmail()));
        assertThat(response.getLastName(), equalTo(userRequest.getLastName()));
        assertThat(response.getRole(), equalTo(RoleDto.USER));
    }

    @Test
    public void shouldNotCreateAnUserWithWrongEmail() throws Exception {

        UserRequest userRequest = someUserRequest();
        userRequest.setEmail("not-an-email");

        mockMvc.perform(post(USER)
                .content(parseToJson(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void shouldDeleteAUser() throws Exception {

        mockMvc.perform(delete(USER + ID, entity1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteAnUserWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete(USER + ID, entity1.getId()))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldUpdateAUser() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("lastName", "an-other-name");
        data.put("postalCode", 100);

        MvcResult mvcResult = mockMvc.perform(patch(USER + ID, entity1.getId())
                .content(parseToJson(data))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto response = (UserDto) JsonUtils.fromJson(mvcResult, UserDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(entity1.getId()));
        assertThat(response.getLastName(), equalTo("an-other-name"));
    }

    @Test
    public void shouldNotUpdateAnUserWhenNotLogged() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("lastName", "an-other-name");
        data.put("postalCode", 100);

        MvcResult mvcResult = mockMvc.perform(patch(USER + ID, entity1.getId())
                .content(parseToJson(data))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }
}
