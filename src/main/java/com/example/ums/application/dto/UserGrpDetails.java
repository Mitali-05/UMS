package com.example.ums.application.dto;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserGrpDetails {
    Integer grp_id;

    @NotBlank(message = "Group name is required")
    String grp_name;

    @Email(message = "Work email must be valid")
    String work_email;

    String phone_no;

    Set<UserSummaryDto> users = new HashSet<>();
    Set<RoleSummaryDto> roles =new HashSet<>();
}
