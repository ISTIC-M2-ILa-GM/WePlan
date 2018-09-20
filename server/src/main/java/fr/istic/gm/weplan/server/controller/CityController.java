package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import lombok.AllArgsConstructor;
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

/**
 * City Controller
 */
@AllArgsConstructor
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
        return cityService.getCities(pageOptions);
    }


    /**
     * Retrieve a city.
     *
     * @param id the id to retrieve
     * @return the city
     */
    @GetMapping(path = ID)
    public CityDto getCity(@PathVariable Long id) {
        return cityService.getCity(id);
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
        return cityService.createCity(cityRequest);
    }

    /**
     * Delete a city.
     *
     * @param id the id to delete
     */
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
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
        return cityService.patchCity(id, patch);
    }
}
