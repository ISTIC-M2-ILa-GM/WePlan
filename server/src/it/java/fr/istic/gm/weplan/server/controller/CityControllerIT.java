package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.infra.repository.CityRepository;
import fr.istic.gm.weplan.server.App;
import fr.istic.gm.weplan.server.config.CommonConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static fr.istic.gm.weplan.server.TestData.someCityDao;
import static fr.istic.gm.weplan.server.config.ApiRoutes.CITY;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class CityControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    private City entity1;
    private City entity2;

    @Before
    public void setUp() {
        cityRepository.deleteAll();

        entity1 = someCityDao();
        entity1.setDepartment(null);
        entity2 = someCityDao();
        entity2.setDepartment(null);

        entity1 = cityRepository.save(entity1);
        entity2 = cityRepository.save(entity2);
    }

    @Test
    public void shouldGetCities() throws Exception {
        mockMvc.perform(get(CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(entity1.getId())))
                .andExpect(jsonPath("$[0].createdAt", is(entity1.getCreatedAt())))
                .andExpect(jsonPath("$[0].updatedAt", is(entity1.getUpdatedAt())))
                .andExpect(jsonPath("$[0].postalCode", is(entity1.getPostalCode())))
                .andExpect(jsonPath("$[1].id", is(entity2.getId())))
                .andExpect(jsonPath("$[1].createdAt", is(entity2.getCreatedAt())))
                .andExpect(jsonPath("$[1].updatedAt", is(entity2.getUpdatedAt())))
                .andExpect(jsonPath("$[1].postalCode", is(entity2.getPostalCode())));
    }
}
