package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.request.UserRequest;

import java.util.Map;

/**
 * The user service definition
 */
public interface UserService {

    /**
     * Retrieve all users.
     *
     * @param pageOptions the page options
     * @return The pages of users
     */
    PageDto<UserDto> getUsers(PageOptions pageOptions);

    /**
     * Retrieve a user.
     *
     * @param id the id of the user
     * @return the user
     */
    UserDto getUser(Long id);

    /**
     * Create a user.
     *
     * @param userRequest the user to create
     * @return the created user
     */
    UserDto createUser(UserRequest userRequest);

    /**
     * Delete a user.
     *
     * @param id the id of the user to delete
     */
    void deleteUser(Long id);

    /**
     * Patch a user.
     *
     * @param id    the id of the user to patch
     * @param patch the map of field to patch
     * @return the updated user
     */
    UserDto patchUser(Long id, Map<String, Object> patch);

}
