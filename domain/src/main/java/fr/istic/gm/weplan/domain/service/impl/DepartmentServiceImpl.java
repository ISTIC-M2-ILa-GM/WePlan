package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService, DepartmentDaoService {

    private DepartmentAdapter departmentAdapter;

    private PersistenceMapper persistenceMapper;

    @Override
    public Department getDepartmentDao(Long id) {
        Optional<Department> department = id != null ? departmentAdapter.findById(id) : Optional.empty();
        if (!department.isPresent() || department.get().getDeletedAt() != null) {
            DomainException e = new DomainException(NOT_FOUND_MSG, Department.class.getSimpleName(), NOT_FOUND);
            throw e;
        }
        return department.get();
    }
}
