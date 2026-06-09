package com.example.ums.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSummaryDto {
    Integer user_id;
    String user_name;
    String email;
    String phone_no;
}
