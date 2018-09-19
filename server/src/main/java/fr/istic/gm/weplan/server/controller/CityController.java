package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.istic.gm.weplan.server.log.LogMessage.API_MESSAGE;
import static fr.istic.gm.weplan.server.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_CREATED;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_DELETED;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CREATE_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.DELETE_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITY;

/**
 * City Controller
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/city", produces = "application/json")
public class CityController {

    private CityService cityService;

    /**
     * Retrieve all cities.
     *
     * @param pageOptions the page options
     * @return the cities pageable
     */
    @GetMapping
    public PageDto<CityDto> getCities(@RequestBody PageOptions pageOptions) {

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
    public CityDto getCity(Long id) {

        log.info(API_MESSAGE, id, GET_CITY, "");
        CityDto city = cityService.getCity(id);
        log.info(API_MESSAGE, id, CITY_GOTTEN, city);

        return city;
    }

    /**
     * Create a city.
     *
     * @param cityRequest the city to create.
     * @return the city created
     */
    public CityDto createCity(CityRequest cityRequest) {

        log.info(API_MESSAGE, "", CREATE_CITY, cityRequest);
        CityDto cityDto = cityService.createCity(cityRequest);
        log.info(API_MESSAGE, "", CITY_CREATED, cityDto);

        return cityDto;
    }

    /**
     * Delete a city.
     * @param id the id to delete
     */
    public void deleteCity(Long id) {

        log.info(API_MESSAGE, id, DELETE_CITY, "");
        cityService.deleteCity(id);
        log.info(API_MESSAGE, id, CITY_DELETED, "");
    }
}
