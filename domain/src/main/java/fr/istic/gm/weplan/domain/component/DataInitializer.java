package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.EventService;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//TODO To delete when admin feature will be done.
/**
 * Temporary class to initialize some data
 */
@AllArgsConstructor
@Component
@Profile("!test")
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private CityService cityService;

    private DepartmentService departmentService;

    private RegionService regionService;

    private ActivityService activityService;

    private EventService eventService;

    private ScheduledEventGenerator scheduledEventGenerator;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        RegionDto bretagne = createOrRetrieveRegion("Bretagne");
        RegionDto provence = createOrRetrieveRegion("Provence-Alpes-Côte d’Azur");
        DepartmentDto illeEtVilaine = createOrRetrieveDepartment(bretagne, "Ille-et-Vilaine");
        DepartmentDto finistere = createOrRetrieveDepartment(bretagne, "Finistère");
        DepartmentDto coteDArmor = createOrRetrieveDepartment(bretagne, "Côtes d'Armor");
        DepartmentDto morbihan = createOrRetrieveDepartment(bretagne, "Morbihan");
        DepartmentDto alpesMaritimes = createOrRetrieveDepartment(provence, "Alpes-Maritimes");
        CityDto stMalo = createOrRetrieveCity(illeEtVilaine, "Saint-Malo", 35400);
        CityDto stBrieuc = createOrRetrieveCity(coteDArmor, "Saint-Brieuc", 2200);
        CityDto brest = createOrRetrieveCity(finistere, "Brest", 29200);
        CityDto benodet = createOrRetrieveCity(finistere, "Bénodet", 29950);
        CityDto vannes = createOrRetrieveCity(morbihan, "Vannes", 56000);
        CityDto lorient = createOrRetrieveCity(morbihan, "Lorient", 56100);
        CityDto nice = createOrRetrieveCity(alpesMaritimes, "Nice", 6000);
        List<Long> waterCities = Arrays.asList(stBrieuc.getId(), stMalo.getId(), brest.getId(), benodet.getId(), vannes.getId(), lorient.getId(), nice.getId());
        List<Long> bzhCities = Arrays.asList(stBrieuc.getId(), stMalo.getId(), brest.getId(), benodet.getId(), vannes.getId(), lorient.getId());
        createOrRetrieveActivity(waterCities, "Sailing", "SAILING");
        createOrRetrieveActivity(waterCities, "Swimming", "SWIMMING");
        createOrRetrieveActivity(Collections.singletonList(stMalo.getId()), "Kart", "KART");
        createOrRetrieveActivity(Collections.singletonList(nice.getId()), "Pétanque", "PETANQUE");
        createOrRetrieveActivity(bzhCities, "Palet", "PALET");
        createEvents();
    }

    private void createEvents() {
        PageDto<EventDto> events = eventService.getEvents(PageRequest.builder().page(0).size(1).build());
        if (events == null || events.getResults() == null || events.getResults().isEmpty()) {
            scheduledEventGenerator.checkWeatherThenGenerateEvents();
        }
    }

    private ActivityDto createOrRetrieveActivity(List<Long> cities, String name, String activityType) {
        try {
            return activityService.getActivity(name);
        } catch (Exception e) {
            return activityService.createActivity(ActivityRequest.builder().name(name).activityType(activityType).citiesId(cities).build());
        }
    }

    private CityDto createOrRetrieveCity(DepartmentDto department, String name, int postalCode) {
        try {
            return cityService.getCity(name);
        } catch (Exception e) {
            return cityService.createCity(CityRequest.builder().name(name).postalCode(postalCode).departmentId(department.getId()).build());
        }
    }

    private DepartmentDto createOrRetrieveDepartment(RegionDto region, String name) {
        try {
            return departmentService.getDepartment(region.getName());
        } catch (Exception e) {
            return departmentService.createDepartment(DepartmentRequest.builder().name(name).regionId(region.getId()).build());
        }
    }

    private RegionDto createOrRetrieveRegion(String name) {
        try {
            return regionService.getRegion(name);
        } catch (Exception e) {
            return regionService.createRegion(RegionRequest.builder().name(name).build());
        }
    }
}
