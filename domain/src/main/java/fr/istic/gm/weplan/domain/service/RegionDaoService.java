package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Region;

public interface RegionDaoService {

    /**
     * Get a region dao for other services.
     * @param id the id to get
     * @return the region
     */
    Region getRegionDao(Long id);
}
