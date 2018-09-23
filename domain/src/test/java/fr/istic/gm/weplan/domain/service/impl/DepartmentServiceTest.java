package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.DepartmentAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static fr.istic.gm.weplan.domain.TestData.someDepartmentRequest;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;
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

    @Mock
    private RegionDaoService mockRegionDaoService;

    @Mock
    private Clock mockClock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new DepartmentServiceImpl(mockDepartmentAdapter, mockRegionDaoService, persistenceMapper, mockClock);
    }

    @Test
    public void shouldGetDepartmentDao() {

        Department department = someDepartment();
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        Department result = service.getDepartmentDao(ID);

        verify(mockDepartmentAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(department));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullDepartmentDao() {

        Optional<Department> optionalDepartment = Optional.empty();

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartmentDao(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedDepartmentDao() {

        Department department = someDepartment();
        department.setDeletedAt(Instant.now());
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartmentDao(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedDao() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartmentDao(null);
    }

    @Test
    public void shouldGetDepartments() {

        PageOptions pageOptions = somePageOptions();
        Page<Department> departments = new PageImpl<>(Collections.singletonList(someDepartment()), PageRequest.of(1, 1), 2);

        when(mockDepartmentAdapter.findAllByDeletedAtIsNull(any())).thenReturn(departments);

        PageDto<DepartmentDto> results = service.getDepartments(pageOptions);

        PageRequest expectedPageable = PageRequest.of(pageOptions.getPage(), pageOptions.getSize());

        verify(mockDepartmentAdapter).findAllByDeletedAtIsNull(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toDepartmentsDto(departments.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetDepartment() {

        Department department = someDepartment();
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        DepartmentDto results = service.getDepartment(ID);

        verify(mockDepartmentAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toDepartmentDto(department)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullDepartment() {

        Optional<Department> optionalDepartment = Optional.empty();

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartment(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedDepartment() {

        Department department = someDepartment();
        department.setDeletedAt(Instant.now());
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartment(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADepartmentWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.getDepartment(null);
    }

    @Test
    public void shouldCreateADepartment() {

        Department department = someDepartment();
        DepartmentRequest departmentRequest = someDepartmentRequest();

        when(mockDepartmentAdapter.save(any())).thenReturn(department);

        DepartmentDto results = service.createDepartment(departmentRequest);

        Department expectedDepartment = persistenceMapper.toDepartment(departmentRequest);

        verify(mockDepartmentAdapter).save(expectedDepartment);

        assertThat(results, notNullValue());
        assertThat(results.getCode(), equalTo(department.getCode()));
        assertThat(results.getName(), equalTo(department.getName()));
    }

    @Test
    public void shouldCreateADepartmentWithRegion() {

        Department department = someDepartment();
        Region region = someRegion();
        DepartmentRequest departmentRequest = someDepartmentRequest();

        when(mockDepartmentAdapter.save(any())).thenReturn(department);
        when(mockRegionDaoService.getRegionDao(any())).thenReturn(region);

        DepartmentDto results = service.createDepartment(departmentRequest);

        verify(mockRegionDaoService).getRegionDao(departmentRequest.getRegionId());

        assertThat(results, notNullValue());
        assertThat(results.getRegion(), notNullValue());
        assertThat(results.getRegion(), equalTo(persistenceMapper.toRegionDto(department.getRegion())));
    }

    @Test
    public void shouldDeleteADepartment() {

        Instant now = Instant.now();
        Department department = someDepartment();
        department.setRegion(null);
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);
        when(mockDepartmentAdapter.save(any())).thenReturn(department);
        when(mockClock.instant()).thenReturn(now);

        service.deleteDepartment(ID);

        verify(mockDepartmentAdapter).findById(ID);
        verify(mockDepartmentAdapter).save(department);
        verify(mockClock).instant();

        assertThat(department.getDeletedAt(), notNullValue());
        assertThat(department.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullDepartment() {

        Optional<Department> optionalDepartment = Optional.empty();

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.deleteDepartment(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedDepartment() {

        Department department = someDepartment();
        department.setDeletedAt(Instant.now());
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.deleteDepartment(ID);
    }

    @Test
    public void shouldPatchADepartment() {

        Department department = someDepartment();
        department.setRegion(null);
        department.setId(ID);
        Optional<Department> optionalDepartment = Optional.of(department);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "a-new-name");
        patch.put("code", 10000);
        patch.put("id", 18);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);
        when(mockDepartmentAdapter.save(any())).thenReturn(department);

        DepartmentDto result = service.patchDepartment(ID, patch);

        verify(mockDepartmentAdapter).findById(ID);
        verify(mockDepartmentAdapter).save(department);

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(ID));
        assertThat(result.getName(), equalTo("a-new-name"));
        assertThat(result.getCode(), equalTo(10000));
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADeletedDepartment() {

        Department department = someDepartment();
        department.setDeletedAt(Instant.now());
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.patchDepartment(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchANullDepartment() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Department.class.getSimpleName()));

        service.patchDepartment(null, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADepartmentWithANullPatch() {

        Department department = someDepartment();
        department.setDeletedAt(null);
        department.setRegion(null);
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchDepartment(ID, null);
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADepartmentWithAnEmptyPatch() {

        Department department = someDepartment();
        department.setDeletedAt(null);
        department.setRegion(null);
        Optional<Department> optionalDepartment = Optional.of(department);

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchDepartment(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowExceptionWhenPatchADepartmentWithWrongType() {

        Department department = someDepartment();
        department.setDeletedAt(null);
        department.setRegion(null);
        Optional<Department> optionalDepartment = Optional.of(department);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", new Object());

        when(mockDepartmentAdapter.findById(any())).thenReturn(optionalDepartment);

        thrown.expect(DomainException.class);
        thrown.expectMessage(WRONG_DATA_TO_PATCH);

        service.patchDepartment(ID, patch);
    }
}
