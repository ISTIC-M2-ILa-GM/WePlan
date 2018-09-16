package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static fr.istic.gm.weplan.server.log.LogMessage.API_MESSAGE;
import static fr.istic.gm.weplan.server.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.server.log.LogMessage.GET_CITIES;

/**
 * City Controller
 */
@AllArgsConstructor
@Slf4j
public class CityController {

    private CityService cityService;

    /**
     * Retrieve all cities.
     * @param pageOptions the page options
     * @return the cities pageable
     */
    public PageDto<CityDto> getCities(PageOptions pageOptions) {

        log.info(API_MESSAGE, GET_CITIES, pageOptions);
        PageDto<CityDto> cities = cityService.getCities(pageOptions);
        log.info(API_MESSAGE, CITIES_GOTTEN, "");

        return cities;
    }
}
