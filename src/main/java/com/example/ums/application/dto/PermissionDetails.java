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
public class PermissionDetails {
    Integer permission_id;

    @NotBlank(message = "Permission Name is required")
    String permission_name;
    String description;

    LocalDateTime created_at;
    LocalDateTime updated_at;

    Set<RoleSummaryDto> roles = new HashSet<>();
}
