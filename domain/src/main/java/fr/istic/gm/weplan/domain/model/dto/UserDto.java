package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"firstName", "lastName", "email"})
@ToString(callSuper = true)
public class UserDto extends BaseDto {

    private String firstName;
    private String lastName;
    private String email;
    private RoleDto role;
    private List<CityDto> cities;
    private List<DepartmentDto> departments;
    private List<RegionDto> regions;
    private List<EventDto> events;
    private List<ActivityDto> activities;
}
