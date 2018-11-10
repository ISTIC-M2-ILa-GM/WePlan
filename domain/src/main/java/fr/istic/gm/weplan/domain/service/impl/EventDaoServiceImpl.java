package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.springframework.data.domain.PageRequest.of;

/**
 * The event dao service impl
 * Separated from EventService to protect from a circular reference
 */
@AllArgsConstructor
@Service
public class EventDaoServiceImpl implements EventDaoService {

    private EventAdapter eventAdapter;

    @Override
    public Page<Event> getEventsDao(PageRequest pageRequest) {
        return eventAdapter.findAll(of(pageRequest.getPage(), pageRequest.getSize()));
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
