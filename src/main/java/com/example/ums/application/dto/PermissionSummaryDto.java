package com.example.ums.application.dto;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionSummaryDto {
    Integer permission_id;
    String permission_name;
    String description;

    LocalDateTime created_at;
    LocalDateTime updated_at;
}
