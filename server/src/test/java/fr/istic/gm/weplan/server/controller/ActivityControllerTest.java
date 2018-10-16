package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someActivity;
import static fr.istic.gm.weplan.server.TestData.someActivityRequest;
import static fr.istic.gm.weplan.server.TestData.somePageActivities;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {

    private ActivityController controller;

    @Mock
    private ActivityService mockActivityService;

    @Before
    public void setUp() {
        controller = new ActivityController(mockActivityService);
    }

    @Test
    public void shouldGetActivitiesPage() {

        PageOptions pageOptions = somePageOptions();
        PageDto<ActivityDto> pageActivities = somePageActivities();

        when(mockActivityService.getActivities(any())).thenReturn(pageActivities);

        PageDto<ActivityDto> result = controller.getActivities(pageOptions);

        verify(mockActivityService).getActivities(pageOptions);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageActivities));
    }

    @Test
    public void shouldGetActivity() {

        ActivityDto activity = someActivity();

        when(mockActivityService.getActivity(any())).thenReturn(activity);

        ActivityDto result = controller.getActivity(ID);

        verify(mockActivityService).getActivity(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(activity));
    }

    @Test
    public void shouldCreateActivity() {

        ActivityRequest activityRequest = someActivityRequest();
        ActivityDto activityDto = someActivity();

        when(mockActivityService.createActivity(any())).thenReturn(activityDto);

        ActivityDto result = controller.postActivity(activityRequest);

        verify(mockActivityService).createActivity(activityRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(activityDto));
    }

    @Test
    public void shouldDeleteActivity() {

        controller.deleteActivity(ID);

        verify(mockActivityService).deleteActivity(ID);
    }

    @Test
    public void shouldPatchActivity() {

        Map<String, Object> patch = new HashMap<>();

        ActivityDto activityDto = someActivity();

        when(mockActivityService.patchActivity(any(), any())).thenReturn(activityDto);

        ActivityDto result = controller.patchActivity(ID, patch);

        verify(mockActivityService).patchActivity(ID, patch);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(activityDto));
    }
}
