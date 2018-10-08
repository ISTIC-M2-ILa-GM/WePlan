package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;
import lombok.ToString;

/**
 * The user request
 */
@Data
@ToString(exclude = {"firstName", "lastName", "email"})
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;

}
