package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DepartmentDto extends BaseDto {

    private String name;
    private int code;
    private RegionDto region;
}
