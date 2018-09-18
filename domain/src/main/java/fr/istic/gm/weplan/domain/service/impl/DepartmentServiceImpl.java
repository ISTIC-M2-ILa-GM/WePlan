package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService, DepartmentDaoService {

    @Override
    public Department getDepartmentDao(Long id) {
        return null;
    }
}
