package com.ra.md05_project.dto.user.register;

import com.ra.md05_project.model.entity.ver1.Roles;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private Boolean status;
    private String avatar;
    private String phone;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean isDeleted;
    private Set<Roles> roles;
}
