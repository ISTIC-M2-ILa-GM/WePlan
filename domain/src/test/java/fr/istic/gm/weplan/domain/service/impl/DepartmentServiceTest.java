package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    private DepartmentServiceImpl service;

    @Mock
    private DepartmentAdapter mockDepartmentAdapter;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new DepartmentServiceImpl(mockDepartmentAdapter, persistenceMapper);
    }

    @Test
    public void shouldGetDaoDepartment() {

        Department department = someDepartment();
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        Department result = service.getDepartmentDao(ID);

        verify(mockDepartmentAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(department));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullCity() {

        Optional<Department> optionalDepartment = Optional.empty();

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartmentDao(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedDao() {

        Department department = someDepartment();
        department.setDeletedAt(Instant.now());
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartmentDao(ID);
    }
}
