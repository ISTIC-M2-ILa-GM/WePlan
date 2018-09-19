package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Department;

import java.util.Optional;

public interface DepartmentAdapter {

    /**
     * Find a department entity by id.
     *
     * @param id the id to search
     * @return the optional department
     */
    Optional<Department> findById(Long id);

}
