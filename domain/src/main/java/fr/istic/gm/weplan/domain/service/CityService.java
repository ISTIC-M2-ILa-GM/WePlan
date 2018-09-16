package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;

public interface CityService {
    PageDto<CityDto> getCities(PageOptions pageOptions);
}
