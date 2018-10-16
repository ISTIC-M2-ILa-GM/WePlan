package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.model.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The Department repository
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentAdapter {

    @Override
    Page<Department> findAll(Pageable pageable);

    @Override
    Page<Department> findAllByDeletedAtIsNull(Pageable pageable);

    @Override
    List<Department> findAllByDeletedAtIsNull();

    @Override
    Optional<Department> findById(Long id);

    @Override
    Department save(Department department);
}
