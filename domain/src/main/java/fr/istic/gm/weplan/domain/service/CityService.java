package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.CityRequest;

import java.util.Map;

/**
 * City Service
 */
public interface CityService {

    /**
     * Retrieve all cities.
     *
     * @param pageOptions the page options
     * @return The pages of Cities
     */
    PageDto<CityDto> getCities(PageOptions pageOptions);

    /**
     * Retrieve a city.
     *
     * @param id the id of the city
     * @return the city
     */
    CityDto getCity(Long id);

    /**
     * Create a city.
     *
     * @param cityRequest the city to create
     * @return the created city
     */
    CityDto createCity(CityRequest cityRequest);

    /**
     * Delete a city.
     *
     * @param id the id of the city to delete
     */
    void deleteCity(Long id);

    /**
     * Patch a city.
     *
     * @param id    the id of the city to patch
     * @param patch the map of field to patch
     * @return the updated city
     */
    CityDto patchCity(Long id, Map<String, Object> patch);
}
