package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentAdapter {

    /**
     * Find all department with pageable.
     *
     * @param pageable the pageable
     * @return the department page
     */
    Page<Department> findAll(Pageable pageable);

    /**
     * Find all department which is not deleted with pageable.
     *
     * @param pageable the pageable
     * @return the department page
     */
    Page<Department> findAllByDeletedAtIsNull(Pageable pageable);

    /**
     * Find a department by id.
     *
     * @param id the id to search
     * @return the optional department
     */
    Optional<Department> findById(Long id);

    /**
     * Save a department.
     *
     * @param department the department to save
     * @return the saved department.
     */
    Department save(Department department);
}
