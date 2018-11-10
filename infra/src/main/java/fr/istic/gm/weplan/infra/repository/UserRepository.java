package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.UserAdapter;
import fr.istic.gm.weplan.domain.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserAdapter {

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    Page<User> findAllByDeletedAtIsNull(Pageable pageable);
}