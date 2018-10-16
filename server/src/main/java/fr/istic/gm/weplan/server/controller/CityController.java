package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;

/**
 * City Controller
 */
@Api(tags = "City Controller")
@AllArgsConstructor
@RestController
@RequestMapping(path = CITY, produces = "application/json")
public class CityController {

    private CityService cityService;

    /**
     * Retrieve all cities.
     *
     * @param pageRequest the page options
     * @return the cities pageable
     */
    @ApiOperation("Get cities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get cities")
    })
    @GetMapping
    public PageDto<CityDto> getCities(@ApiParam(value = "Page request") @RequestBody(required = false) PageRequest pageRequest) {
        return cityService.getCities(pageRequest);
    }


    /**
     * Retrieve a city.
     *
     * @param id the id to retrieve
     * @return the city
     */
    @ApiOperation("Get a city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get a city with a given id"),
            @ApiResponse(code = 404, message = "City not found")
    })
    @GetMapping(path = ID)
    public CityDto getCity(@ApiParam(value = "City id", required = true) @PathVariable Long id) {
        return cityService.getCity(id);
    }

    /**
     * Create a city.
     *
     * @param cityRequest the city to create.
     * @return the city created
     */
    @ApiOperation("Create a city")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create a city")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CityDto createCity(@Valid @ApiParam(value = "City request", required = true) @RequestBody CityRequest cityRequest) {
        return cityService.createCity(cityRequest);
    }

    /**
     * Delete a city.
     *
     * @param id the id to delete
     */
    @ApiOperation("Delete a city")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete a city with a given id"),
            @ApiResponse(code = 404, message = "City not found")
    })
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCity(@ApiParam(value = "City id", required = true) @PathVariable Long id) {
        cityService.deleteCity(id);
    }

    /**
     * Patch a city with new data.
     *
     * @param id    the id of the city to patch
     * @param patch the map of the field to patch
     * @return the updated city
     */
    @ApiOperation("Patch a city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch a city with a given id"),
            @ApiResponse(code = 404, message = "City not found")
    })
    @PatchMapping(path = ID)
    public CityDto patchCity(@ApiParam(value = "City id", required = true) @PathVariable Long id, @ApiParam(value = "Patch request", required = true) @RequestBody Map<String, Object> patch) {
        return cityService.patchCity(id, patch);
    }
}
