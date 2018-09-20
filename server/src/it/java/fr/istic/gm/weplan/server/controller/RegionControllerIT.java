package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.infra.repository.RegionRepository;
import fr.istic.gm.weplan.server.App;
import fr.istic.gm.weplan.server.config.ApiRoutes;
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

import static fr.istic.gm.weplan.server.TestData.someRegionDao;
import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.ApiRoutes.REGION;
import static fr.istic.gm.weplan.server.utils.JsonUtils.parseToJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, CommonConfiguration.class})
@AutoConfigureMockMvc
public class RegionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    private Region entity1;
    private Region entity2;

    @Before
    public void setUp() {
        regionRepository.deleteAll();

        this.entity1 = someRegionDao();
        this.entity1.setName(null);
        this.entity1.setDeletedAt(null);

        this.entity2 = someRegionDao();
        this.entity2.setName(null);
        this.entity2.setDeletedAt(null);

        this.entity1 = regionRepository.save(entity1);
        this.entity2 = regionRepository.save(entity2);
    }

    @Test
    public void shouldGetRegions() throws Exception {

        PageOptions pageOptions = new PageOptions();
        pageOptions.setPage(0);
        pageOptions.setSize(10);

        MvcResult mvcResult = mockMvc.perform(get(REGION)
                .content(parseToJson(pageOptions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PageDto<RegionDto> response = (PageDto<RegionDto>) JsonUtils.fromJson(mvcResult, PageDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getTotalPages(), equalTo(1));
        assertThat(response.getSize(), equalTo(10));
        assertThat(response.getResults(), hasSize(2));
    }

    @Test
    public void shouldGetRegion() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(REGION + ID, this.entity1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RegionDto response = (RegionDto) JsonUtils.fromJson(mvcResult, RegionDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(this.entity1.getId()));
        assertThat(response.getName(), equalTo(this.entity1.getName()));
    }

    @Test
    public void shouldPostRegion() throws Exception {
        RegionDto regionDto = new RegionDto();
        regionDto.setName("Bretagne");

        MvcResult mvcResult = mockMvc.perform(post(REGION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseToJson(regionDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        RegionDto response = (RegionDto) JsonUtils.fromJson(mvcResult, RegionDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), notNullValue());
        assertThat(response.getName(), equalTo(regionDto.getName()));
    }

    @Test
    public void shouldPatchRegion() throws Exception {
        RegionDto regionDto = new RegionDto();
        regionDto.setName("Bretagne");

        MvcResult mvcResult = mockMvc.perform(patch(REGION + ID, this.entity1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseToJson(regionDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RegionDto response = (RegionDto) JsonUtils.fromJson(mvcResult, RegionDto.class);

        assertThat(response, notNullValue());
        assertThat(response.getId(), equalTo(this.entity1.getId()));
        assertThat(response.getName(), equalTo(regionDto.getName()));
    }


    @Test
    public void shouldDeleteRegion() throws Exception {
        mockMvc.perform(delete(REGION + ApiRoutes.ID, this.entity1.getId())).andExpect(status().isNoContent());

        mockMvc.perform(delete(REGION + ApiRoutes.ID, this.entity2.getId())).andExpect(status().isNoContent());
    }
}
