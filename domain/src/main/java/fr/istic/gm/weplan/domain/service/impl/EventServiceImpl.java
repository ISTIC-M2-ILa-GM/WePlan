package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Service
public class EventServiceImpl extends PatchService<Event> implements EventService, EventDaoService {

    private EventAdapter eventAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<EventDto> getEvents(PageRequest pageRequest) {

        Page<Event> events = eventAdapter.findAllByDeletedAtIsNull(org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
        return persistenceMapper.toEventsPageDto(events);
    }

    @Override
    public EventDto getEvent(Long id) {

        Event event = getEventDao(id);
        return persistenceMapper.toEventDto(event);
    }

    @Override
    public void deleteEvent(Long id) {

        Event event = getEventDao(id);
        event.setDeletedAt(clock.instant());
        eventAdapter.save(event);
    }

    @Override
    public Event getEventDao(Long id) {

        Optional<Event> event = id != null ? eventAdapter.findById(id) : Optional.empty();
        if (!event.isPresent() || event.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Event.class.getSimpleName(), NOT_FOUND);
        }
        return event.get();
    }
}
