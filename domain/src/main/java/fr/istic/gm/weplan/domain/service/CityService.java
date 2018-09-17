package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;

/**
 * City Service
 */
public interface CityService {

    /**
     * Retrieve all cities.
     * @param pageOptions the page options
     * @return The pages of Cities
     */
    PageDto<CityDto> getCities(PageOptions pageOptions);

    /**
     * Retrieve a city
     * @param id the id of the city
     * @return the city
     */
    CityDto getCity(Long id);
}
