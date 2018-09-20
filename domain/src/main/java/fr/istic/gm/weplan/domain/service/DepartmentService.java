package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;

import java.util.Map;

/**
 * The department service definition
 */
public interface DepartmentService {

    /**
     * Retrieve all departments.
     *
     * @param pageOptions the page options
     * @return The pages of departments
     */
    PageDto<DepartmentDto> getDepartments(PageOptions pageOptions);

    /**
     * Retrieve a department.
     *
     * @param id the id of the department
     * @return the department
     */
    DepartmentDto getDepartment(Long id);

    /**
     * Create a department.
     *
     * @param departmentRequest the department to create
     * @return the created department
     */
    DepartmentDto createDepartment(DepartmentRequest departmentRequest);

    /**
     * Delete a department.
     *
     * @param id the id of the department to delete
     */
    void deleteDepartment(Long id);

    /**
     * Patch a department.
     *
     * @param id    the id of the department to patch
     * @param patch the map of field to patch
     * @return the updated department
     */
    DepartmentDto patchDepartment(Long id, Map<String, Object> patch);

}
