package fr.istic.gm.weplan.infra.repository.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.model.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeRegionRepository implements RegionAdapter {
    private long AUTO_INCREMENT = 0;
    private Region region = new Region();
    private List<Region> regionList = new ArrayList<>();

    @Override
    public Page<Region> findAll(Pageable pageable) {
        return new PageImpl<>(this.regionList);
    }


    @Override
    public Optional<Region> findById(Long id) {
        this.region.setId(0L);
        this.regionList.add(this.region);

        return this.regionList.stream().filter(region -> id.equals(region.getId())).findFirst();
    }

    @Override
    public Region save(Region region) {
        region.setId(AUTO_INCREMENT++);
        region.setCreatedAt(Instant.now());
        region.setUpdatedAt(Instant.now());

        this.regionList.add(region);
        return region;
    }
}
