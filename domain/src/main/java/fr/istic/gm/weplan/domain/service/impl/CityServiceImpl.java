package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static fr.istic.gm.weplan.domain.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.domain.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.domain.log.LogMessage.SERVICE_MESSAGE;

@AllArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private CityAdapter cityAdapter;

    private PersistenceMapper persistenceMapper;

    @Override
    public PageDto<CityDto> getCities(PageOptions pageOptions) {

        log.info(SERVICE_MESSAGE, "", GET_CITIES, pageOptions);

        Page<City> cities = cityAdapter.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        PageDto<CityDto> citiesDto = PageDto.<CityDto>builder()
                .results(persistenceMapper.toCitiesDto(cities.getContent()))
                .totalPages(cities.getTotalPages())
                .size(cities.getSize())
                .build();

        log.info(SERVICE_MESSAGE, "", CITIES_GOTTEN, "");

        return citiesDto;
    }
}
