package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someDepartment;
import static fr.istic.gm.weplan.server.TestData.someDepartmentRequest;
import static fr.istic.gm.weplan.server.TestData.somePageDepartments;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {

    private DepartmentController controller;

    @Mock
    private DepartmentService mockDepartmentService;

    @Before
    public void setUp() {
        controller = new DepartmentController(mockDepartmentService);
    }

    @Test
    public void shouldGetDepartmentsPage() {

        PageRequest pageRequest = somePageOptions();
        PageDto<DepartmentDto> pageDepartments = somePageDepartments();

        when(mockDepartmentService.getDepartments(any())).thenReturn(pageDepartments);

        PageDto<DepartmentDto> result = controller.getDepartments(pageRequest);

        verify(mockDepartmentService).getDepartments(pageRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageDepartments));
    }

    @Test
    public void shouldGetADepartment() {

        DepartmentDto department = someDepartment();

        when(mockDepartmentService.getDepartment(anyLong())).thenReturn(department);

        DepartmentDto result = controller.getDepartment(ID);

        verify(mockDepartmentService).getDepartment(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(department));
    }

    @Test
    public void shouldCreateADepartment() {

        DepartmentRequest departmentRequest = someDepartmentRequest();
        DepartmentDto departmentDto = someDepartment();

        when(mockDepartmentService.createDepartment(any())).thenReturn(departmentDto);

        DepartmentDto result = controller.createDepartment(departmentRequest);

        verify(mockDepartmentService).createDepartment(departmentRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(departmentDto));
    }

    @Test
    public void shouldDeleteADepartment() {

        controller.deleteDepartment(ID);

        verify(mockDepartmentService).deleteDepartment(ID);
    }

    @Test
    public void shouldPatchADepartment() {

        Map<String, Object> patch = new HashMap<>();

        DepartmentDto departmentDto = someDepartment();

        when(mockDepartmentService.patchDepartment(any(), any())).thenReturn(departmentDto);

        DepartmentDto result = controller.patchDepartment(ID, patch);

        verify(mockDepartmentService).patchDepartment(ID, patch);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(departmentDto));
    }
}
