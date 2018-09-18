package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.*;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_CREATED;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_GOTTEN;
import static fr.istic.gm.weplan.domain.log.LogMessage.CREATE_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.domain.log.LogMessage.GET_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.SERVICE_MESSAGE;

@AllArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private CityAdapter cityAdapter;

    private DepartmentService departmentService;

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

    @Override
    public CityDto getCity(Long id) {

        log.info(SERVICE_MESSAGE, id, GET_CITY, "");

        Optional<City> city = getAndVerifyCity(id);
        CityDto cityDto = persistenceMapper.toCityDto(city.get());

        log.info(SERVICE_MESSAGE, id, CITY_GOTTEN, cityDto);

        return cityDto;
    }

    @Override
    public CityDto createCity(CityRequest cityRequest) {

        log.info(SERVICE_MESSAGE, "", CREATE_CITY, cityRequest);

        City city = persistenceMapper.toCity(cityRequest);
        city.setDepartment(departmentService.getDepartmentDao(cityRequest.getDepartmentId()));
        City result = cityAdapter.save(city);
        CityDto cityDto = persistenceMapper.toCityDto(result);

        log.info(SERVICE_MESSAGE, "", CITY_CREATED, cityDto);

        return cityDto;
    }

    private Optional<City> getAndVerifyCity(Long id) {
        Optional<City> city = cityAdapter.findById(id);
        if (!city.isPresent()) {
            DomainException e = new DomainException(NOT_FOUND_MSG, "city", NOT_FOUND);
            log.error(e.getMessage(), e);
            throw e;
        }
        return city;
    }
}
