package fr.istic.gm.weplan.server.exception;

import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.infra.repository.RegionRepository;
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

import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.ApiRoutes.REGION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class AppExceptionHandlerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    private Region entity1;

    @Before
    public void setUp(){
        this.regionRepository.deleteAll();
        this.entity1 = new Region();
        this.entity1.setName("Bretagne");
        this.regionRepository.save(entity1);
    }

    @Test
    public void shouldReturnNotFoundErrorCode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(REGION + ID, 999)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponse response = (ErrorResponse) JsonUtils.fromJson(mvcResult, ErrorResponse.class);
        assertThat(response.getCode(), equalTo(AppError.DOMAIN_ENTITY_NOT_FOUND));
        assertThat(response.getObject(), equalTo("Region"));
    }
}