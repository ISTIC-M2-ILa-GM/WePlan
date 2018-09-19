package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Department;

/**
 * The department DAO service definition
 */
public interface DepartmentDaoService {

    /**
     * Get a department dao for other services.
     * @param id the id to get
     * @return the department
     */
    Department getDepartmentDao(Long id);
}
