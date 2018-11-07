package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.adapter.EventBrokerAdapter;
import fr.istic.gm.weplan.domain.adapter.WeatherAdapter;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.domain.service.ActivityDaoService;
import fr.istic.gm.weplan.domain.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ScheduledEventGenerator {
    private WeatherAdapter weatherAdapter;

    private EventService eventService;

    private ActivityDaoService activityDaoService;

    private EventBrokerAdapter eventBrokerAdapter;


    /**
     * Generates events every thursday at 6:00pm
     */
    @Scheduled(cron = "0 18 0 * * 4")
    public void checkWeatherThenGenerateEvents() {
        List<Activity> activitiesDao = this.activityDaoService.getActivitiesDao();
        activitiesDao.forEach(activity -> {
            activity.getCities().forEach(city -> {
                Week weatherWeek = this.weatherAdapter.getWeatherWeek(city.getPostalCode());

                List<Weather> clearWeather = weatherWeek.getWeathers().stream().filter(w -> {
                    ZonedDateTime zonedDateTime = w.getDate().atZone(ZoneId.of("Europe/Paris"));
                    DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();

                    // returns the weather of the weekend
                    return (dayOfWeek.equals(DayOfWeek.SATURDAY) ||
                            dayOfWeek.equals(DayOfWeek.SUNDAY)) &&
                            zonedDateTime.getHour() >= 8 &&
                            zonedDateTime.getHour() <= 18; // returns the weather between 8h and 18h
                }).filter(w -> w.getCode().startsWith("80")).collect(Collectors.toList()); // returns only clear weather

                clearWeather.forEach(w -> {
                    Event event = new Event();
                    event.setCity(city);
                    event.setActivity(activity);
                    event.setDate(w.getDate());
                    event.setCanceled(false);

                    // create and save event in DB
                    EventDto eventDto = this.eventService.createEvent(event);
                    this.eventBrokerAdapter.sendEvent(eventDto);
                });
            });
        });
    }
}
