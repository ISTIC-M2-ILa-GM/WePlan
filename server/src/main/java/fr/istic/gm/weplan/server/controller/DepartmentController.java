package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.DEPARTMENT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;

/**
 * Department Controller
 */
@Api(tags = "Department Controller", description = "Department API")
@AllArgsConstructor
@RestController
@RequestMapping(path = DEPARTMENT, produces = "application/json")
public class DepartmentController {

    private DepartmentService departmentService;

    /**
     * Retrieve all departments.
     *
     * @param pageOptions the page options
     * @return the departments pageable
     */
    @ApiOperation("Get departments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get departments")
    })
    @GetMapping
    public PageDto<DepartmentDto> getDepartments(@ApiParam(value = "Page request") @RequestBody(required = false) PageOptions pageOptions) {
        return departmentService.getDepartments(pageOptions);
    }

    /**
     * Retrieve a department.
     *
     * @param id the id to retrieve
     * @return the department
     */
    @ApiOperation("Get a department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get a department with a given id"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(path = ID)
    public DepartmentDto getDepartment(@ApiParam(value = "Department id", required = true) @PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    /**
     * Create a department.
     *
     * @param departmentRequest the department to create.
     * @return the department created
     */
    @ApiOperation("Create a department")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create a department")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartmentDto createDepartment(@Valid @ApiParam(value = "Department request", required = true) @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.createDepartment(departmentRequest);
    }

    /**
     * Delete a department.
     *
     * @param id the id to delete
     */
    @ApiOperation("Delete a city")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete a city with a given id"),
            @ApiResponse(code = 404, message = "City not found")
    })
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDepartment(@ApiParam(value = "Department id", required = true) @PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    /**
     * Patch a department with new data.
     *
     * @param id    the id of the department to patch
     * @param patch the map of the field to patch
     * @return the updated department
     */
    @ApiOperation("Patch a department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch a department with a given id"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @PatchMapping(path = ID)
    public DepartmentDto patchDepartment(@ApiParam(value = "Department id", required = true) @PathVariable Long id, @ApiParam(value = "Patch request", required = true) @RequestBody Map<String, Object> patch) {
        return departmentService.patchDepartment(id, patch);
    }
}
