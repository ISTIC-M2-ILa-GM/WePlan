package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.RegionService;
import fr.istic.gm.weplan.domain.service.impl.RegionServiceImpl;
import fr.istic.gm.weplan.infra.repository.impl.FakeRegionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static fr.istic.gm.weplan.server.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
@Path("/region")
public class RegionController {
    private RegionService regionService;

    public RegionController() {
        this.regionService = new RegionServiceImpl(new FakeRegionRepository(), Mappers.getMapper(PersistenceMapper.class));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        log.info(API_MESSAGE, "", GET_REGIONS, pageOptions);
        PageDto<RegionDto> regions = this.regionService.getRegions(pageOptions);
        log.info(API_MESSAGE, "", REGIONS_GOTTEN, "");

        return regions;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RegionDto getRegion(@PathParam("id") Long id) {
        return this.regionService.getRegion(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegionDto createRegion(RegionDto region) {
        return this.regionService.createRegion(region);
    }
}
