package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.impl.CityServiceImpl;
import fr.istic.gm.weplan.infra.repository.impl.CityRepositoryImpl;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static fr.istic.gm.weplan.server.log.LogMessage.API_MESSAGE;
import static fr.istic.gm.weplan.server.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_CREATED;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CREATE_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITY;

/**
 * City Controller
 */
@AllArgsConstructor
@Slf4j
@Path("/city")
@Api(value="/city", description = "The city controller")
public class CityController {

    private CityService cityService;

    /**
     * Temporary Constructor for jaxrs
     */
    public CityController() {
        cityService = new CityServiceImpl(new CityRepositoryImpl(), (DepartmentService) id -> null, Mappers.getMapper(PersistenceMapper.class));
    }

    /**
     * Retrieve all cities.
     *
     * @param pageOptions the page options
     * @return the cities pageable
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageDto<CityDto> getCities(PageOptions pageOptions) {

        log.info(API_MESSAGE, "", GET_CITIES, pageOptions);
        PageDto<CityDto> cities = cityService.getCities(pageOptions);
        log.info(API_MESSAGE, "", CITIES_GOTTEN, "");

        return cities;
    }


    /**
     * Retrieve a city.
     *
     * @param id the id to retrieve
     * @return the city
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CityDto getCity(@PathParam("id") Long id) {

        log.info(API_MESSAGE, id, GET_CITY, "");
        CityDto city = cityService.getCity(id);
        log.info(API_MESSAGE, "", CITY_GOTTEN, city);

        return city;
    }

    /**
     * Create a city.
     *
     * @param cityRequest the city to create.
     * @return the city created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CityDto createCity(CityRequest cityRequest) {

        log.info(API_MESSAGE, "", CREATE_CITY, cityRequest);
        CityDto cityDto = cityService.createCity(cityRequest);
        log.info(API_MESSAGE, "", CITY_CREATED, cityDto);

        return cityDto;
    }
}
