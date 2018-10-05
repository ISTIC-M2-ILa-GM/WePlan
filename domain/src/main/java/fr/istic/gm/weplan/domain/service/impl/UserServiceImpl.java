package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.UserAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.Role;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.UserDaoService;
import fr.istic.gm.weplan.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Slf4j
@Service
public class UserServiceImpl extends PatchService<User> implements UserService, UserDaoService {

    private UserAdapter userAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public User getUserDao(Long id) {

        Optional<User> user = id != null ? userAdapter.findById(id) : Optional.empty();
        if (!user.isPresent() || user.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, User.class.getSimpleName(), NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public PageDto<UserDto> getUsers(PageOptions pageOptions) {

        Page<User> users = userAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        return persistenceMapper.toUsersPageDto(users);
    }

    @Override
    public UserDto getUser(Long id) {

        User user = getUserDao(id);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserRequest userRequest) {

        User user = persistenceMapper.toUser(userRequest);
        user.setRole(Role.USER);
        User result = userAdapter.save(user);
        return persistenceMapper.toUserDto(result);
    }

    @Override
    public void deleteUser(Long id) {

        User user = getUserDao(id);
        user.setDeletedAt(clock.instant());
        userAdapter.save(user);
    }

    @Override
    public UserDto patchUser(Long id, Map<String, Object> data) {

        User user = getUserDao(id);
        patch(user, data);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    protected String[] getIgnoreFieldToPatch() {
        return new String[]{"role"};
    }
}
