package com.ra.md05_project.dto.user.register;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserRequestDTO {
    private Long userId;

//    @Uni(entityClass = User.class, fieldName = "username", message = "Username already exists.", entityIdField = "userId")
    @NotBlank
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

}
