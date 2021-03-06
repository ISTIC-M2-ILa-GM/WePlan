package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.CityDaoService;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.springframework.data.domain.PageRequest.of;

@AllArgsConstructor
@Service
public class CityServiceImpl extends PatchService<City> implements CityService, CityDaoService {

    private CityAdapter cityAdapter;

    private DepartmentDaoService departmentDaoService;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<CityDto> getCities(PageRequest pageRequest) {

        if (pageRequest != null) {
            Page<City> cities = cityAdapter.findAllByDeletedAtIsNull(of(pageRequest.getPage(), pageRequest.getSize()));
            return persistenceMapper.toCitiesPageDto(cities);
        }
        List<City> cities = cityAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toCitiesPageDto(cities);
    }

    @Override
    public List<CityDto> getCities() {
        List<City> cities = cityAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toCitiesDto(cities);
    }

    @Override
    public CityDto getCity(Long id) {

        City city = getCityDao(id);
        return persistenceMapper.toCityDto(city);
    }

    @Override
    public CityDto getCity(String name) {

        Optional<City> city = name != null ? cityAdapter.findByName(name) : Optional.empty();
        if (!city.isPresent() || city.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, City.class.getSimpleName(), NOT_FOUND);
        }
        return persistenceMapper.toCityDto(city.get());
    }

    @Override
    public CityDto createCity(CityRequest cityRequest) {

        City city = persistenceMapper.toCity(cityRequest);
        city.setDepartment(departmentDaoService.getDepartmentDao(cityRequest.getDepartmentId()));
        City result = cityAdapter.save(city);
        return persistenceMapper.toCityDto(result);
    }

    @Override
    public void deleteCity(Long id) {

        City city = getCityDao(id);
        city.setDeletedAt(clock.instant());
        cityAdapter.save(city);
    }

    @Override
    public CityDto patchCity(Long id, Map<String, Object> data) {

        City city = getCityDao(id);
        patch(city, data);
        city = cityAdapter.save(city);
        return persistenceMapper.toCityDto(city);
    }

    @Override
    public City getCityDao(Long id) {

        Optional<City> city = id != null ? cityAdapter.findById(id) : Optional.empty();
        if (!city.isPresent() || city.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, City.class.getSimpleName(), NOT_FOUND);
        }
        return city.get();
    }

    @Override
    public List<City> getCitiesDao() {
        return this.cityAdapter.findAllByDeletedAtIsNull();
    }
}
