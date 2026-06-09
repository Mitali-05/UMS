package com.example.ums.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetails {
    Integer user_id;

    @NotBlank(message = "Username is required")
    String user_name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email;

    String phone_no;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    Set<UserGrpSummaryDto> grps =new HashSet<>();
    Set<RoleSummaryDto> roles =new HashSet<>();
}
