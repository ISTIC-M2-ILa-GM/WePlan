package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.infra.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;

import static fr.istic.gm.weplan.infra.TestData.someUser;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User entity1;
    private User entity2;

    @Before
    public void setUp() {

        userRepository.deleteAll();

        entity1 = userRepository.save(someUser());
        entity2 = userRepository.save(someUser());
    }

    @Test
    public void shouldFindAll() {

        Page<User> users = userRepository.findAll(PageRequest.of(0, 10));

        assertThat(users, notNullValue());
        assertThat(users.getTotalPages(), equalTo(1));
        assertThat(users.getContent(), hasSize(2));
        assertThat(users.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNull() {

        entity1.setDeletedAt(Instant.now());
        entity1 = userRepository.save(entity1);

        Page<User> users = userRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));

        assertThat(users, notNullValue());
        assertThat(users.getTotalPages(), equalTo(1));
        assertThat(users.getContent(), hasSize(1));
        assertThat(users.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllWithPage() {

        Page<User> users = userRepository.findAll(PageRequest.of(0, 1));

        assertThat(users, notNullValue());
        assertThat(users.getTotalPages(), equalTo(2));
        assertThat(users.getContent(), hasSize(1));
        assertThat(users.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetOneUser() {

        Optional<User> user = userRepository.findById(entity1.getId());

        assertThat(user, notNullValue());
        assertThat(user.isPresent(), equalTo(true));
        assertThat(user.get().getEmail(), equalTo(entity1.getEmail()));
        assertThat(user.get().getFirstName(), equalTo(entity1.getFirstName()));
        assertThat(user.get().getLastName(), equalTo(entity1.getLastName()));
        assertThat(user.get().getPassword(), equalTo(entity1.getPassword()));
        assertThat(user.get().getRole(), equalTo(entity1.getRole()));
        assertThat(user.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldCreateAUser() {

        User user = someUser();

        user = userRepository.save(user);

        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getCreatedAt(), notNullValue());
        assertThat(user.getUpdatedAt(), notNullValue());
    }
}
