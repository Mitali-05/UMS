package com.example.ums.application.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDetails {
    Integer role_id;

    @NotBlank(message = "Role name is required")
    String role_name;
    String description;

    LocalDateTime created_at;
    LocalDateTime updated_at;

    Set<UserSummaryDto> users = new HashSet<>();
    Set<UserGrpSummaryDto> grps = new HashSet<>();
    Set<PermissionSummaryDto> permissions =new HashSet<>();
}
