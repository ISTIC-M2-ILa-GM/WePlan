package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static fr.istic.gm.weplan.server.config.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.log.LogMessage.API_MESSAGE;
import static fr.istic.gm.weplan.server.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_CREATED;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_DELETED;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.CITY_PATCHED;
import static fr.istic.gm.weplan.server.log.LogMessage.CREATE_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.DELETE_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITY;
import static fr.istic.gm.weplan.server.log.LogMessage.PATCH_CITY;

/**
 * City Controller
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = CITY, produces = "application/json")
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
    @GetMapping(path = ID)
    public CityDto getCity(@PathVariable Long id) {

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
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CityDto createCity(@RequestBody CityRequest cityRequest) {

        log.info(API_MESSAGE, "", CREATE_CITY, cityRequest);
        CityDto cityDto = cityService.createCity(cityRequest);
        log.info(API_MESSAGE, "", CITY_CREATED, cityDto);

        return cityDto;
    }

    /**
     * Delete a city.
     *
     * @param id the id to delete
     */
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id) {

        log.info(API_MESSAGE, id, DELETE_CITY, "");
        cityService.deleteCity(id);
        log.info(API_MESSAGE, id, CITY_DELETED, "");
    }

    /**
     * Patch a city with new data.
     *
     * @param id    the id of the city to patch
     * @param patch the map of the field to patch
     * @return the updated city
     */
    @PatchMapping(path = ID)
    public CityDto patchCity(@PathVariable Long id, @RequestBody Map<String, Object> patch) {

        log.info(API_MESSAGE, id, PATCH_CITY, patch);
        CityDto cityDto = cityService.patchCity(id, patch);
        log.info(API_MESSAGE, id, CITY_PATCHED, cityDto);

        return cityDto;
    }
}
