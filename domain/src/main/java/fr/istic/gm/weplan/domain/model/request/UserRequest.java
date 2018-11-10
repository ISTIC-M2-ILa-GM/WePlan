package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * The user request
 */
@Data
@ToString(exclude = {"firstName", "lastName", "email", "password"})
public class UserRequest {

    private String firstName;
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}
