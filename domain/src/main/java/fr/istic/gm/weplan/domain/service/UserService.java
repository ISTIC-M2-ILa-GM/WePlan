package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.request.UserRequest;

import java.util.List;
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
     * Retrieve an user.
     *
     * @param id the id of the user
     * @return the user
     */
    UserDto getUser(Long id);

    /**
     * Retrieve an user
     *
     * @param name the name of the user
     * @return the user
     */
    UserDto getUserByEmail(String name);

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

    /**
     * Add cities to an user.
     *
     * @param id       the id of the user
     * @param citiesId the id list of the cities id to add to the user
     * @return the updated user
     */
    UserDto addCities(Long id, List<Long> citiesId);

    /**
     * Add departments to an user
     *
     * @param id            the id of the user
     * @param departmentsId the id list of the departments id to add to the user
     * @return the updated user
     */
    UserDto addDepartments(Long id, List<Long> departmentsId);

    /**
     * Add regions to an user
     *
     * @param id        the id of the user
     * @param regionsId the id list of the regions id to add to the user
     * @return the updated user
     */
    UserDto addRegions(Long id, List<Long> regionsId);

    /**
     * Add activities to an user
     *
     * @param id           the id of the user
     * @param activitiesId the id list of the activities id to add to the user
     * @return the updated user
     */
    UserDto addActivities(Long id, List<Long> activitiesId);

    /**
     * Add events to an user
     *
     * @param id       the id of the user
     * @param eventsId the id list of the events id to add to the user
     * @return the updated user
     */
    UserDto addEvents(Long id, List<Long> eventsId);

    /**
     * Remove cities to an user.
     *
     * @param id       the id of the user
     * @param citiesId the id list of the cities id to remove from the user
     * @return the updated user
     */
    UserDto removeCities(Long id, List<Long> citiesId);

    /**
     * Remove departments to an user
     *
     * @param id            the id of the user
     * @param departmentsId the id list of the departments id to remove from the user
     * @return the updated user
     */
    UserDto removeDepartments(Long id, List<Long> departmentsId);

    /**
     * Remove regions to an user
     *
     * @param id        the id of the user
     * @param regionsId the id list of the regions id to remove from the user
     * @return the updated user
     */
    UserDto removeRegions(Long id, List<Long> regionsId);

    /**
     * Remove activities to an user
     *
     * @param id           the id of the user
     * @param activitiesId the id list of the activities id to remove from the user
     * @return the updated user
     */
    UserDto removeActivities(Long id, List<Long> activitiesId);

    /**
     * Remove events to an user
     *
     * @param id       the id of the user
     * @param eventsId the id list of the events id to remove from the user
     * @return the updated user
     */
    UserDto removeEvents(Long id, List<Long> eventsId);
}
