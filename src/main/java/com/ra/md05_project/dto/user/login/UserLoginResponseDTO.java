package com.ra.md05_project.dto.user.login;

import com.ra.md05_project.model.entity.ver1.Role;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private Long userId;
    private String username;
    private Set<Role> roles;
//-------------------------------
    private String accessToken;
    private String typeToken;
}
