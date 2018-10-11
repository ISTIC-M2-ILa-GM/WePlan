package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.DEPARTMENT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;

/**
 * Event Controller
 */
@Api(tags = "Event Controller", description = "Event API")
@AllArgsConstructor
@RestController
@RequestMapping(path = DEPARTMENT, produces = "application/json")
public class EventController {

    private EventService eventService;

    /**
     * Retrieve all events.
     *
     * @param pageOptions the page options
     * @return the events pageable
     */
    @ApiOperation("Get events")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get events")
    })
    @GetMapping
    public PageDto<EventDto> getEvents(@ApiParam(value = "Page request", required = true) @RequestBody PageOptions pageOptions) {
        return eventService.getEvents(pageOptions);
    }


    /**
     * Retrieve an event.
     *
     * @param id the id to retrieve
     * @return the event
     */
    @ApiOperation("Get an event")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get an event with a given id"),
            @ApiResponse(code = 404, message = "Event not found")
    })
    @GetMapping(path = ID)
    public EventDto getEvent(@ApiParam(value = "Event id", required = true) @PathVariable Long id) {
        return eventService.getEvent(id);
    }
}
