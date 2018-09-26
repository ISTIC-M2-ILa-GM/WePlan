package fr.istic.gm.weplan.domain.model.weather;

import lombok.Data;

import java.util.List;

@Data
public class Week {
    private List<Weather> weathers;
}
