package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
import fr.istic.gm.weplan.domain.service.RegionService;
import fr.istic.gm.weplan.domain.service.PatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Slf4j
@Service
public class DepartmentServiceImpl extends PatchService<Department> implements DepartmentService, DepartmentDaoService {

    private DepartmentAdapter departmentAdapter;

    private RegionDaoService regionDaoService;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public Department getDepartmentDao(Long id) {

        Optional<Department> department = id != null ? departmentAdapter.findById(id) : Optional.empty();
        if (!department.isPresent() || department.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Department.class.getSimpleName(), NOT_FOUND);
        }
        return department.get();
    }

    @Override
    public PageDto<DepartmentDto> getDepartments(PageOptions pageOptions) {

        Page<Department> departments = departmentAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        return persistenceMapper.toDepartmentsPageDto(departments);
    }

    @Override
    public DepartmentDto getDepartment(Long id) {

        Department department = getDepartmentDao(id);
        return persistenceMapper.toDepartmentDto(department);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentRequest departmentRequest) {

        Department department = persistenceMapper.toDepartment(departmentRequest);
        department.setRegion(regionDaoService.getRegionDao(departmentRequest.getRegionId()));
        Department result = departmentAdapter.save(department);
        return persistenceMapper.toDepartmentDto(result);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = getDepartmentDao(id);
        department.setDeletedAt(clock.instant());
        departmentAdapter.save(department);
    }

    @Override
    public DepartmentDto patchDepartment(Long id, Map<String, Object> data) {

        Department department = getDepartmentDao(id);
        patch(department, data);
        department = departmentAdapter.save(department);
        return persistenceMapper.toDepartmentDto(department);
    }
}
