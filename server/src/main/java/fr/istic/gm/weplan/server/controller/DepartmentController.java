package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static fr.istic.gm.weplan.server.config.ApiRoutes.DEPARTMENT;
import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;

/**
 * Department Controller
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = DEPARTMENT, produces = "application/json")
public class DepartmentController {

    private DepartmentService departmentService;

    /**
     * Retrieve all cities.
     *
     * @param pageOptions the page options
     * @return the cities pageable
     */
    @GetMapping
    public PageDto<DepartmentDto> getDepartments(@RequestBody PageOptions pageOptions) {
        return departmentService.getDepartments(pageOptions);
    }


    /**
     * Retrieve a department.
     *
     * @param id the id to retrieve
     * @return the department
     */
    @GetMapping(path = ID)
    public DepartmentDto getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    /**
     * Create a department.
     *
     * @param departmentRequest the department to create.
     * @return the department created
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return departmentService.createDepartment(departmentRequest);
    }

    /**
     * Delete a department.
     *
     * @param id the id to delete
     */
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    /**
     * Patch a department with new data.
     *
     * @param id    the id of the department to patch
     * @param patch the map of the field to patch
     * @return the updated department
     */
    @PatchMapping(path = ID)
    public DepartmentDto patchDepartment(@PathVariable Long id, @RequestBody Map<String, Object> patch) {
        return departmentService.patchDepartment(id, patch);
    }
}
