package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.adapter.WeatherAdapter;
import fr.istic.gm.weplan.domain.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledEventGenerator {
    private WeatherAdapter weatherAdapter;

    private EventService eventService;


    /**
     * Generates events every thursday at midnight
     */
    @Scheduled(cron = "0 0 0 * * 4")
    public void checkWeatherThenGenerateEvents(){
        // Step 1: retrieve all cities and activities
        // Step 2: loop over the different locations
        // Step 3: retrieve weather for each location
        // Step 4: generate a List<Event>
        // Step 5: save List<Event> in DB
        // TODO: Step 6: broadcast events
    }
}
