package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.infra.repository.CityRepository;
import fr.istic.gm.weplan.infra.repository.DepartmentRepository;
import fr.istic.gm.weplan.server.App;
import fr.istic.gm.weplan.server.config.CommonConfiguration;
import fr.istic.gm.weplan.server.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.firstPageOptions;
import static fr.istic.gm.weplan.server.TestData.someCityDao;
import static fr.istic.gm.weplan.server.TestData.someCityRequest;
import static fr.istic.gm.weplan.server.TestData.someDepartmentDao;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGIN;
import static fr.istic.gm.weplan.server.utils.JsonUtils.parseToJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class CityControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PersistenceMapper persistenceMapper;

    private City entity1;
    private City entity2;
    private Department department;

    @Before
    public void setUp() {
        cityRepository.deleteAll();
        departmentRepository.deleteAll();

        department = someDepartmentDao();
        department.setRegion(null);
        department = departmentRepository.save(department);

        entity1 = someCityDao();
        entity1.setDepartment(department);
        entity1.setDeletedAt(null);
        entity2 = someCityDao();
        entity2.setDepartment(department);
        entity2.setDeletedAt(null);

        entity1 = cityRepository.save(entity1);
        entity2 = cityRepository.save(entity2);
    }

    @Test
    @WithMockUser
    public void shouldGetCities() throws Exception {

        PageOptions pageOptions = new PageOptions();
        pageOptions.setPage(0);
        pageOptions.setSize(10);

        MvcResult mvcResult = mockMvc.perform(get(CITY)
                .content(parseToJson(pageOptions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PageDto<CityDto> response = (PageDto<CityDto>) JsonUtils.fromJson(mvcResult, PageDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getTotalPages(), equalTo(1));
        assertThat(response.getSize(), equalTo(10));
        assertThat(response.getResults(), hasSize(2));
    }

    @Test
    @WithMockUser
    public void shouldGetCitiesWithoutPage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(CITY)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PageDto<CityDto> response = (PageDto<CityDto>) JsonUtils.fromJson(mvcResult, PageDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getTotalPages(), equalTo(1));
        assertThat(response.getSize(), equalTo(2));
        assertThat(response.getResults(), hasSize(2));
    }

    @Test
    public void shouldNotGetCitiesWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(CITY)
                .content(parseToJson(firstPageOptions()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldGetACity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(CITY + ID, entity1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CityDto response = (CityDto) JsonUtils.fromJson(mvcResult, CityDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(entity1.getId()));
        assertThat(response.getName(), equalTo(entity1.getName()));
        assertThat(response.getPostalCode(), equalTo(entity1.getPostalCode()));
        assertThat(response.getDepartment(), equalTo(persistenceMapper.toDepartmentDto(entity1.getDepartment())));
    }

    @Test
    public void shouldNotGetACityWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(CITY + ID, entity1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldCreateACity() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setDepartmentId(department.getId());

        MvcResult mvcResult = mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CityDto response = (CityDto) JsonUtils.fromJson(mvcResult, CityDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), notNullValue());
        assertThat(response.getName(), equalTo(cityRequest.getName()));
        assertThat(response.getPostalCode(), equalTo(cityRequest.getPostalCode()));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldNotCreateACityWithoutDepartment() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setDepartmentId(null);

        mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldNotCreateACityWithEmptyName() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setName("");
        cityRequest.setDepartmentId(department.getId());

        mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldNotCreateACityWithoutName() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setName(null);
        cityRequest.setDepartmentId(department.getId());

        mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldNotCreateACityWithoutPostalCode() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setPostalCode(null);
        cityRequest.setDepartmentId(department.getId());

        mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateACityWhenNotLogged() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setDepartmentId(department.getId());

        MvcResult mvcResult = mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldNotCreateACityWhenNotLoggedAsAdmin() throws Exception {

        CityRequest cityRequest = someCityRequest();
        cityRequest.setDepartmentId(department.getId());

        mockMvc.perform(post(CITY)
                .content(parseToJson(cityRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldDeleteACity() throws Exception {

        mockMvc.perform(delete(CITY + ID, entity1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteACityWhenNotLogged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete(CITY + ID, entity1.getId()))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldNotDeleteACityWhenNotLoggedAsAdmin() throws Exception {

        mockMvc.perform(delete(CITY + ID, entity1.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void shouldUpdateACity() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "an-other-name");
        data.put("postalCode", 100);

        MvcResult mvcResult = mockMvc.perform(patch(CITY + ID, entity1.getId())
                .content(parseToJson(data))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CityDto response = (CityDto) JsonUtils.fromJson(mvcResult, CityDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(entity1.getId()));
        assertThat(response.getName(), equalTo("an-other-name"));
        assertThat(response.getPostalCode(), equalTo(100));
    }

    @Test
    public void shouldNotUpdateACityWhenNotLogged() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "an-other-name");
        data.put("postalCode", 100);

        MvcResult mvcResult = mockMvc.perform(patch(CITY + ID, entity1.getId())
                .content(parseToJson(data))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(LOCATION), equalTo("http://localhost" + LOGIN));
    }

    @Test
    @WithMockUser
    public void shouldNotUpdateACityWhenNotLoggedAsAdmin() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "an-other-name");
        data.put("postalCode", 100);

        mockMvc.perform(patch(CITY + ID, entity1.getId())
                .content(parseToJson(data))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
