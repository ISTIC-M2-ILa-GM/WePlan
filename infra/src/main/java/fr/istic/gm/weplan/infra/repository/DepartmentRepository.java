package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.model.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The Department repository
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentAdapter {

    @Override
    Optional<Department> findById(Long id);
}
