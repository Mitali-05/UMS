package com.example.ums.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleSummaryDto {
    Integer role_id;
    String role_name;
    String description;

    LocalDateTime created_at;
    LocalDateTime updated_at;
}
