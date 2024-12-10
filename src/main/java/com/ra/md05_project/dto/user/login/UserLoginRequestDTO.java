package com.ra.md05_project.dto.user.login;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserLoginRequestDTO {

    private String username;
    private String password;
}