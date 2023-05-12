package com.digital.helpdesk.dto;
import com.digital.helpdesk.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Must be not empty. Please, provide a valid nickname")
    private String nickname;

    @Email
    private String email;

    private String name;

    @NotEmpty(message = "Must be not empty")
    @Length(min = 5, message = "You need to provide a password that contains at least 5 characters")
    private String password;

    @NotEmpty(message = "Must be not empty")
    private String lastName;

    private boolean active;

    private List<Role> roles;
}
