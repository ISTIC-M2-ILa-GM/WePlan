package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Temporary class to initialize some data
 */
@AllArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private CityService cityService;

    private DepartmentService departmentService;

    private RegionService regionService;

    private ActivityService activityService;

    private ScheduledEventGenerator scheduledEventGenerator;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        RegionDto bretagne = regionService.createRegion(RegionRequest.builder().name("Bretagne").build());
        RegionDto provence = regionService.createRegion(RegionRequest.builder().name("Provence-Alpes-Côte d’Azur").build());
        DepartmentDto illeEtVilaine = departmentService.createDepartment(DepartmentRequest.builder().name("Ille-et-Vilaine").regionId(bretagne.getId()).build());
        DepartmentDto finistere = departmentService.createDepartment(DepartmentRequest.builder().name("Finistère").regionId(bretagne.getId()).build());
        DepartmentDto coteDArmor = departmentService.createDepartment(DepartmentRequest.builder().name("Côtes d'Armor").regionId(bretagne.getId()).build());
        DepartmentDto morbihan = departmentService.createDepartment(DepartmentRequest.builder().name("Morbihan").regionId(bretagne.getId()).build());
        DepartmentDto alpesMaritimes = departmentService.createDepartment(DepartmentRequest.builder().name("Alpes-Maritimes").regionId(provence.getId()).build());
        CityDto stMalo = cityService.createCity(CityRequest.builder().name("Saint-Malo").postalCode(35400).departmentId(illeEtVilaine.getId()).build());
        CityDto stBrieuc = cityService.createCity(CityRequest.builder().name("Saint-Brieuc").postalCode(2200).departmentId(coteDArmor.getId()).build());
        CityDto brest = cityService.createCity(CityRequest.builder().name("Brest").postalCode(29200).departmentId(finistere.getId()).build());
        CityDto benodet = cityService.createCity(CityRequest.builder().name("Bénodet").postalCode(29950).departmentId(finistere.getId()).build());
        CityDto vannes = cityService.createCity(CityRequest.builder().name("Vannes").postalCode(56000).departmentId(morbihan.getId()).build());
        CityDto lorient = cityService.createCity(CityRequest.builder().name("Lorient").postalCode(56100).departmentId(morbihan.getId()).build());
        CityDto nice = cityService.createCity(CityRequest.builder().name("Nice").postalCode(6000).departmentId(alpesMaritimes.getId()).build());
        List<Long> water = Arrays.asList(stBrieuc.getId(), stMalo.getId(), brest.getId(), benodet.getId(), vannes.getId(), lorient.getId(), nice.getId());
        List<Long> bzh = Arrays.asList(stBrieuc.getId(), stMalo.getId(), brest.getId(), benodet.getId(), vannes.getId(), lorient.getId());
        activityService.createActivity(ActivityRequest.builder().name("Sailing").activityType("SAILING").citiesId(water).build());
        activityService.createActivity(ActivityRequest.builder().name("Swimming").activityType("SWIMMING").citiesId(water).build());
        activityService.createActivity(ActivityRequest.builder().name("Kart").activityType("KART").citiesId(Collections.singletonList(stMalo.getId())).build());
        activityService.createActivity(ActivityRequest.builder().name("Pétanque").activityType("PETANQUE").citiesId(Collections.singletonList(nice.getId())).build());
        activityService.createActivity(ActivityRequest.builder().name("Palet").activityType("PALET").citiesId(bzh).build());
        scheduledEventGenerator.checkWeatherThenGenerateEvents();
    }
}
