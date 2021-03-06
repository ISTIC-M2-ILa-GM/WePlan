package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.springframework.data.domain.PageRequest.of;

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
    public PageDto<DepartmentDto> getDepartments(PageRequest pageRequest) {

        if (pageRequest != null) {
            Page<Department> departments = departmentAdapter.findAllByDeletedAtIsNull(of(pageRequest.getPage(), pageRequest.getSize()));
            return persistenceMapper.toDepartmentsPageDto(departments);
        }
        List<Department> departments = departmentAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toDepartmentsPageDto(departments);
    }

    @Override
    public List<DepartmentDto> getDepartments() {

        List<Department> departments = departmentAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toDepartmentsDto(departments);
    }

    @Override
    public DepartmentDto getDepartment(Long id) {

        Department department = getDepartmentDao(id);
        return persistenceMapper.toDepartmentDto(department);
    }

    @Override
    public DepartmentDto getDepartment(String name) {

        Optional<Department> department = name != null ? departmentAdapter.findByName(name) : Optional.empty();
        if (!department.isPresent() || department.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Department.class.getSimpleName(), NOT_FOUND);
        }
        return persistenceMapper.toDepartmentDto(department.get());
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
