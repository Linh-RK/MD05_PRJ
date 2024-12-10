package com.ra.md05_project.dto.user.update;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean isDeleted;
}
