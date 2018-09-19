package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.someCityDao;
import static fr.istic.gm.weplan.server.TestData.someDepartmentDao;
import static fr.istic.gm.weplan.server.config.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.utils.JsonUtils.parseToJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

    private City entity1;
    private City entity2;

    @Before
    public void setUp() {
        cityRepository.deleteAll();
        departmentRepository.deleteAll();

        entity1 = someCityDao();
        entity1.setDepartment(null);
        entity1.setDeletedAt(null);
        entity2 = someCityDao();
        entity2.setDepartment(null);
        entity2.setDeletedAt(null);

        entity1 = cityRepository.save(entity1);
        entity2 = cityRepository.save(entity2);
    }

    @Test
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
        assertThat(response.getDepartment(), equalTo(entity1.getDepartment()));
    }

    @Test
    public void shouldCreateACity() throws Exception {

        Department department = someDepartmentDao();
        department.setRegion(null);
        department = departmentRepository.save(department);

        CityRequest cityRequest = new CityRequest();
        cityRequest.setName("a-name");
        cityRequest.setPostalCode(1000);
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
    public void shouldDeleteACity() throws Exception {

        mockMvc.perform(delete(CITY + ID, entity1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
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
}
