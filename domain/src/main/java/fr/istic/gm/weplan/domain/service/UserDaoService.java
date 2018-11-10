package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.User;

/**
 * The user DAO service definition
 */
public interface UserDaoService {

    /**
     * Get an user dao for other services.
     *
     * @param id the id to get
     * @return the user
     */
    User getUserDao(Long id);

    /**
     * Get an user dao for other services.
     *
     * @param email the email
     * @return the user
     */
    User getUserDaoByEmail(String email);
}
