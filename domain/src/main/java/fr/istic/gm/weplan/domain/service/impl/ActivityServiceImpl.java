package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.ActivityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.ActivityDaoService;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityDaoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Slf4j
@Service
public class ActivityServiceImpl extends PatchService<Activity> implements ActivityService, ActivityDaoService {
    private ActivityAdapter activityAdapter;

    private CityDaoService cityDaoService;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public Activity getActivityDao(Long id) {

        Optional<Activity> activity = id != null ? activityAdapter.findById(id) : Optional.empty();
        if (!activity.isPresent() || activity.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Activity.class.getSimpleName(), NOT_FOUND);
        }
        return activity.get();
    }

    @Override
    public List<Activity> getActivitiesDao() {
        return this.activityAdapter.findAllByDeletedAtIsNull();
    }

    @Override
    public PageDto<ActivityDto> getActivities(PageRequest pageRequest) {

        if (pageRequest != null) {
            Page<Activity> activities = activityAdapter.findAllByDeletedAtIsNull(org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
            return persistenceMapper.toActivitiesPageDto(activities);
        }
        List<Activity> activities = activityAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toActivitiesPageDto(activities);
    }

    @Override
    public List<ActivityDto> getActivities() {

        List<Activity> activities = activityAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toActivitiesDto(activities);
    }

    @Override
    public ActivityDto getActivity(Long id) {
        Activity activity = this.getActivityDao(id);

        return this.persistenceMapper.toActivityDto(activity);
    }

    @Override
    public ActivityDto getActivity(String name) {

        Optional<Activity> activity = name != null ? activityAdapter.findByName(name) : Optional.empty();
        if (!activity.isPresent() || activity.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Activity.class.getSimpleName(), NOT_FOUND);
        }

        return this.persistenceMapper.toActivityDto(activity.get());
    }

    @Override
    public ActivityDto createActivity(ActivityRequest activityRequest) {
        Activity input = this.persistenceMapper.toActivity(activityRequest);

        if (activityRequest.getCitiesId() != null) {
            List<City> cities = activityRequest.getCitiesId().stream().map(id -> cityDaoService.getCityDao(id)).collect(Collectors.toList());
            input.setCities(cities);
        }

        Activity result = this.activityAdapter.save(input);

        return this.persistenceMapper.toActivityDto(result);
    }

    @Override
    public ActivityDto patchActivity(Long id, Map<String, Object> map) {
        Activity activity = this.getActivityDao(id);

        this.patch(activity, map);

        Activity result = this.activityAdapter.save(activity);

        return this.persistenceMapper.toActivityDto(result);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity activity = this.getActivityDao(id);
        activity.setDeletedAt(this.clock.instant());

        this.activityAdapter.save(activity);
    }
}