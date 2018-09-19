package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.infra.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static fr.istic.gm.weplan.infra.TestData.someCity;
import static fr.istic.gm.weplan.infra.TestData.someDepartment;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void shouldGetOneDepartment() {

        Optional<Department> department = departmentRepository.findById(entity1.getId());

        assertThat(department, notNullValue());
        assertThat(department.isPresent(), equalTo(true));
        assertThat(department.get().getName(), equalTo(entity1.getName()));
        assertThat(department.get().getRegion(), equalTo(entity1.getRegion()));
        assertThat(department.get().getId(), equalTo(entity1.getId()));
    }
}
