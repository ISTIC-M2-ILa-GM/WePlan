package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.Department;
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
import java.util.List;
import java.util.Optional;

import static fr.istic.gm.weplan.infra.TestData.someDepartment;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department entity1;
    private Department entity2;

    @Before
    public void setUp() {

        departmentRepository.deleteAll();

        entity1 = someDepartment();
        entity1.setRegion(null);
        entity2 = someDepartment();
        entity2.setRegion(null);

        entity1 = departmentRepository.save(entity1);
        entity2 = departmentRepository.save(entity2);
    }

    @Test
    public void shouldFindAll() {

        Page<Department> departments = departmentRepository.findAll(PageRequest.of(0, 10));

        assertThat(departments, notNullValue());
        assertThat(departments.getTotalPages(), equalTo(1));
        assertThat(departments.getContent(), hasSize(2));
        assertThat(departments.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNullPage() {

        entity1.setDeletedAt(Instant.now());
        entity1 = departmentRepository.save(entity1);

        Page<Department> departments = departmentRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));

        assertThat(departments, notNullValue());
        assertThat(departments.getTotalPages(), equalTo(1));
        assertThat(departments.getContent(), hasSize(1));
        assertThat(departments.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNull() {

        entity1.setDeletedAt(Instant.now());
        entity1 = departmentRepository.save(entity1);

        List<Department> departments = departmentRepository.findAllByDeletedAtIsNull();

        assertThat(departments, notNullValue());
        assertThat(departments, hasSize(1));
    }

    @Test
    public void shouldFindAllWithPage() {

        Page<Department> departments = departmentRepository.findAll(PageRequest.of(0, 1));

        assertThat(departments, notNullValue());
        assertThat(departments.getTotalPages(), equalTo(2));
        assertThat(departments.getContent(), hasSize(1));
        assertThat(departments.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetOneDepartment() {

        Optional<Department> department = departmentRepository.findById(entity1.getId());

        assertThat(department, notNullValue());
        assertThat(department.isPresent(), equalTo(true));
        assertThat(department.get().getName(), equalTo(entity1.getName()));
        assertThat(department.get().getCode(), equalTo(entity1.getCode()));
        assertThat(department.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldCreateADepartment() {

        Department department = someDepartment();
        department.setId(null);
        department.setCreatedAt(null);
        department.setUpdatedAt(null);
        department.setRegion(null);

        department = departmentRepository.save(department);

        assertThat(department, notNullValue());
        assertThat(department.getId(), notNullValue());
        assertThat(department.getCreatedAt(), notNullValue());
        assertThat(department.getUpdatedAt(), notNullValue());
    }
}
