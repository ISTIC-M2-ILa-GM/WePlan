package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.ActivityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.PatchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Service
public class ActivityServiceImpl extends PatchService<Activity> implements ActivityService {

    private ActivityAdapter activityAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<ActivityDto> getActivities(PageOptions pageOptions) {

        Page<Activity> activities = activityAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        return persistenceMapper.toActivitiesPageDto(activities);
    }

    @Override
    public ActivityDto getActivity(Long id) {

        Activity activity = getAndVerifyActivity(id);
        return persistenceMapper.toActivityDto(activity);
    }

    @Override
    public ActivityDto createActivity(ActivityRequest activityRequest) {

        Activity activity = persistenceMapper.toActivity(activityRequest);
         // activity.setCities
        Activity result = activityAdapter.save(activity);
        return persistenceMapper.toActivityDto(result);
    }

    @Override
    public void deleteActivity(Long id) {

        Activity activity = getAndVerifyActivity(id);
        activity.setDeletedAt(clock.instant());
        activityAdapter.save(activity);
    }

    @Override
    public ActivityDto patchActivity(Long id, Map<String, Object> data) {

        Activity activity = getAndVerifyActivity(id);
        patch(activity, data);
        activity = activityAdapter.save(activity);
        return persistenceMapper.toActivityDto(activity);
    }

    private Activity getAndVerifyActivity(Long id) {

        Optional<Activity> activity = id != null ? activityAdapter.findById(id) : Optional.empty();
        if (!activity.isPresent() || activity.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Activity.class.getSimpleName(), NOT_FOUND);
        }
        return activity.get();
    }
}
