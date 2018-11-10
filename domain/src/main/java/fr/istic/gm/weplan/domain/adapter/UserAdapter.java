package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The user adapter
 */
public interface UserAdapter {


    /**
     * Find all user with pageable.
     *
     * @param pageable the pageable
     * @return the user page
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find all user which is not deleted with pageable.
     *
     * @param pageable the pageable
     * @return the user page
     */
    Page<User> findAllByDeletedAtIsNull(Pageable pageable);

    /**
     * Find an user by id.
     *
     * @param id the id to search
     * @return the optional user
     */
    Optional<User> findById(Long id);

    /**
     * Find an user by email
     *
     * @param email the email
     * @return the user
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Save a user.
     *
     * @param user the user to save
     * @return the saved user.
     */
    User save(User user);
}
