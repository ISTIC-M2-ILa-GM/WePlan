package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityAdapter {
    Page<City> findAll(Pageable pageable);
}
