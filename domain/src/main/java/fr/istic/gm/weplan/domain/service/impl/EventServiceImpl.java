package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.AuthAdapter;
import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.EventService;
import fr.istic.gm.weplan.domain.service.UserDaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@AllArgsConstructor
@Service
public class EventServiceImpl extends PatchService<Event> implements EventService {

    private EventAdapter eventAdapter;

    private EventDaoService eventDaoService;

    private UserDaoService userDaoService;

    private AuthAdapter authAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<EventDto> getEvents(PageRequest pageRequest) {

        Long id = authAdapter.loggedUserId();

        User user = userDaoService.getUserDao(id);

        List<City> cities = user.getCities();
        List<Activity> activities = user.getActivities();

        Page<Event> events = eventAdapter.findAllByCitiesAndActivities(of(pageRequest.getPage(), pageRequest.getSize()), cities, activities);
        return persistenceMapper.toEventsPageDto(events);
    }

    @Override
    public EventDto getEvent(Long id) {

        Event event = eventDaoService.getEventDao(id);
        return persistenceMapper.toEventDto(event);
    }

    @Override
    public void deleteEvent(Long id) {

        Event event = eventDaoService.getEventDao(id);
        event.setDeletedAt(clock.instant());
        eventAdapter.save(event);
    }

    @Override
    public EventDto createEvent(Event event) {
        Event result = eventAdapter.save(event);
        return persistenceMapper.toEventDto(result);
    }
}
