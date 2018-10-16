package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.RegionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static fr.istic.gm.weplan.server.TestData.somePageRegions;
import static fr.istic.gm.weplan.server.TestData.someRegion;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegionControllerTest {
    private RegionController regionController;

    @Mock
    private RegionService mockRegionService;

    @Before
    public void setUp() {
        this.regionController = new RegionController(this.mockRegionService);
    }


    @Test
    public void shouldGetRegionsPage() {
        PageOptions pageOptions = somePageOptions();
        PageDto<RegionDto> pageCities = somePageRegions();

        // prepare stub for getRegions method
        when(mockRegionService.getRegions(any())).thenReturn(pageCities);

        PageDto<RegionDto> result = this.regionController.getRegions(pageOptions);

        verify(this.mockRegionService).getRegions(pageOptions);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageCities));
    }

/*
    @Test
    public void shouldGetRegion() {

    }
*/

    @Test
    public void shouldPostRegion() {
        RegionRequest regionRequest = new RegionRequest();
        regionRequest.setName("Bretagne");

        RegionDto someRegion = someRegion();
        when(this.mockRegionService.createRegion(any())).thenReturn(someRegion);

        RegionDto postResult = regionController.postRegion(regionRequest);

        verify(this.mockRegionService).createRegion(regionRequest);

        assertThat(postResult, notNullValue());
        assertThat(postResult, equalTo(someRegion));
    }
}
