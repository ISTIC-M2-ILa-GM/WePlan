package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.PatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.BAD_REQUEST;
import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITIES_GOTTEN;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_CREATED;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_DELETED;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_GOTTEN;
import static fr.istic.gm.weplan.domain.log.LogMessage.CITY_PATCHED;
import static fr.istic.gm.weplan.domain.log.LogMessage.CREATE_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.DELETE_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.GET_CITIES;
import static fr.istic.gm.weplan.domain.log.LogMessage.GET_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.PATCH_CITY;
import static fr.istic.gm.weplan.domain.log.LogMessage.SERVICE_MESSAGE;

@AllArgsConstructor
@Slf4j
@Service
public class CityServiceImpl extends PatchService<City> implements CityService {

    private CityAdapter cityAdapter;

    private DepartmentDaoService departmentDaoService;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<CityDto> getCities(PageOptions pageOptions) {

        log.info(SERVICE_MESSAGE, "", GET_CITIES, pageOptions);

        Page<City> cities = cityAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        PageDto<CityDto> citiesDto = persistenceMapper.toCitiesPageDto(cities);

        log.info(SERVICE_MESSAGE, "", CITIES_GOTTEN, "");

        return citiesDto;
    }

    @Override
    public CityDto getCity(Long id) {

        log.info(SERVICE_MESSAGE, id, GET_CITY, "");

        City city = getAndVerifyCity(id);
        CityDto cityDto = persistenceMapper.toCityDto(city);

        log.info(SERVICE_MESSAGE, id, CITY_GOTTEN, cityDto);

        return cityDto;
    }

    @Override
    public CityDto createCity(CityRequest cityRequest) {

        log.info(SERVICE_MESSAGE, "", CREATE_CITY, cityRequest);

        City city = persistenceMapper.toCity(cityRequest);
        city.setDepartment(departmentDaoService.getDepartmentDao(cityRequest.getDepartmentId()));
        City result = cityAdapter.save(city);
        CityDto cityDto = persistenceMapper.toCityDto(result);

        log.info(SERVICE_MESSAGE, "", CITY_CREATED, cityDto);

        return cityDto;
    }

    @Override
    public void deleteCity(Long id) {

        log.info(SERVICE_MESSAGE, id, DELETE_CITY, "");

        City city = getAndVerifyCity(id);
        city.setDeletedAt(clock.instant());
        cityAdapter.save(city);

        log.info(SERVICE_MESSAGE, id, CITY_DELETED, "");
    }

    @Override
    public CityDto patchCity(Long id, Map<String, Object> data) {

        log.info(SERVICE_MESSAGE, id, PATCH_CITY, data);

        City city = getAndVerifyCity(id);
        if (data == null || data.isEmpty()) {
            DomainException e = new DomainException(NOTHING_TO_PATCH, City.class.getSimpleName(), BAD_REQUEST);
            log.error(e.getMessage(), e);
            throw e;
        }
        patch(city, data);
        city = cityAdapter.save(city);
        CityDto cityDto = persistenceMapper.toCityDto(city);

        log.info(SERVICE_MESSAGE, id, CITY_PATCHED, cityDto);

        return cityDto;
    }

    private City getAndVerifyCity(Long id) {

        Optional<City> city = id != null ? cityAdapter.findById(id) : Optional.empty();
        if (!city.isPresent() || city.get().getDeletedAt() != null) {
            DomainException e = new DomainException(NOT_FOUND_MSG, City.class.getSimpleName(), NOT_FOUND);
            log.error(SERVICE_MESSAGE, id, e.getMessage(), "", e);
            throw e;
        }
        return city.get();
    }
}
